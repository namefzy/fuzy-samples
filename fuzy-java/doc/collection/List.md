# `ArrayList`

## 1.概述

​	`ArrayList`实现了`List`接口，元素存放的数据与放进去的顺序相同，允许放入`null`元素，底层通过数组实现，支持自动扩容。

## 2.类图

![List类图](https://image-1301573777.cos.ap-chengdu.myqcloud.com/20201012223634.png)

由类图可知`ArrayList`实现了4个接口，分别是：

- List接口，提供数组的添加、删除、修改、迭代遍历等操作
- [`RandomAccess`](https://github.com/YunaiV/openjdk/blob/master/src/java.base/share/classes/java/util/RandomAccess.java) 接口，表示 `ArrayList` 支持**快速**的随机访问
- `Serializabel`接口，支持序列化功能
- `Cloneable`接口，支持克隆

## 3.属性

```java
//无参构造添加第一个元素时会默认初始化容量
private static final int DEFAULT_CAPACITY = 10;
//空的数组
private static final Object[] EMPTY_ELEMENTDATA = {};
//无参构造默认的数组地址
private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
//元素数组，当初始化无参构造数组时，会默认this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA
//当添加第一个元素时，会将数组扩大至DEFAULT_CAPACITY容量
transient Object[] elementData; 
//数组时机大小
private int size;
```

![ArrayList](https://image-1301573777.cos.ap-chengdu.myqcloud.com/20201012225742.png)

- `elementData`属性

  ​	元素数组，构造一个空`ArrayList`时，该属性被初始化`DEFAULTCAPACITY_EMPTY_ELEMENTDATA`；图中红色空格代表我们已经添加元素，白色空格代表我们并未使用。

- `size`属性

  ​	数组大小。注意，`size` 代表的是 `ArrayList` 已使用 `elementData` 的元素的数量，对于开发者看到的 `#size()` 也是该大小。并且，当我们添加新的元素时，恰好其就是元素添加到 `elementData` 的位置（下标）。当然，我们知道 `ArrayList` **真正**的大小是 `elementData` 的大小。

## 4.构造方法

### 4.1无参构造

```java
private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
public ArrayList() {
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}
```

### 4.2有参构造-初始化数组大小

```java
private static final Object[] EMPTY_ELEMENTDATA = {};
public ArrayList(int initialCapacity) {
    if (initialCapacity > 0) {
        this.elementData = new Object[initialCapacity];
    } else if (initialCapacity == 0) {
        this.elementData = EMPTY_ELEMENTDATA;
    } else {
        throw new IllegalArgumentException("Illegal Capacity: "+
                                           initialCapacity);
    }
}
```

### 4.3有参构造-传入集合

```java
public ArrayList(Collection<? extends E> c) {
    elementData = c.toArray();
    if ((size = elementData.length) != 0) {
        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, size, Object[].class);
    } else {
        this.elementData = EMPTY_ELEMENTDATA;
    }
}
```

> - 在我们学习`ArrayList`的时候，一直被灌输了一个概念，在未设置初始化容量时，`ArrayList`默认大小为 10 。但是此处，我们可以看到初始化为 `DEFAULTCAPACITY_EMPTY_ELEMENTDATA` 这个空数组。这是为什么呢？`ArrayList` 考虑到节省内存，一些使用场景下仅仅是创建了`ArrayList`对象，实际并未使用。所以，`ArrayList`优化成初始化是个空数组，在首次添加元素时，才真正初始化为容量为 10 的数组。
> - 那么为什么单独声明了 `DEFAULTCAPACITY_EMPTY_ELEMENTDATA` 空数组，而不直接使用 `EMPTY_ELEMENTDATA` 呢？在下文中，我们会看到 `DEFAULTCAPACITY_EMPTY_ELEMENTDATA` 首次扩容为 10 ，而 `EMPTY_ELEMENTDATA` 按照 **1.5 倍**扩容从 0 开始而不是 10 。 两者的起点不同。

## 5.添加元素

### 5.1添加单个元素

```java
/**
 * 添加单个元素
 */
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}
private void ensureCapacityInternal(int minCapacity) {
    //当无参构造初始化集合时，第一次添加元素初始化数组容量为10
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
    }

    ensureExplicitCapacity(minCapacity);
}
private void ensureExplicitCapacity(int minCapacity) {
    //记录修改次数
    modCount++;
	//扩容
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}

```

### 5.2指定位置添加一个元素

```java
public void add(int index, E element) {
    //校验索引是否非法
    rangeCheckForAdd(index);
	//修改modcount次数，增加容量
    ensureCapacityInternal(size + 1); 
    //从index+1位置复制数组
    System.arraycopy(elementData, index, elementData, index + 1,
                     size - index);
    elementData[index] = element;
    size++;
}
```



![ArrayList_add](https://image-1301573777.cos.ap-chengdu.myqcloud.com/20201014211340.png)

​																				**add添加元素**

## 6.数组容量

### 6.1数组扩容

```java
private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
```

![ArrayList_grow](https://image-1301573777.cos.ap-chengdu.myqcloud.com/20201014211103.png)

​																		**集合扩容过程**

### 6.2数组缩容

```java
public void trimToSize() {
    modCount++;
    if (size < elementData.length) {
        elementData = (size == 0)
            ? EMPTY_ELEMENTDATA
            : Arrays.copyOf(elementData, size);
    }
}
```

## 7.移除元素

### 7.1移除元素

```java
public boolean remove(Object o) {
    if (o == null) {
        for (int index = 0; index < size; index++)
            if (elementData[index] == null) {
                fastRemove(index);
                return true;
            }
    } else {
        for (int index = 0; index < size; index++)
            if (o.equals(elementData[index])) {
                fastRemove(index);
                return true;
            }
    }
    return false
}

private void fastRemove(int index) {
    modCount++;
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    elementData[--size] = null; // clear to let GC do its work
}
```

### 7.2移除多个元素

```java
public boolean removeAll(Collection<?> c) {
    Objects.requireNonNull(c);
    return batchRemove(c, false);
}
private boolean batchRemove(Collection<?> c, boolean complement) {
    final Object[] elementData = this.elementData;
    int r = 0, w = 0;
    boolean modified = false;
    try {
        for (; r < size; r++)
            if (c.contains(elementData[r]) == complement)
                elementData[w++] = elementData[r];
    } finally {
        // Preserve behavioral compatibility with AbstractCollection,
        // even if c.contains() throws.
        //如果 contains 方法发生异常执行该方法
        if (r != size) {
            //elementData就是数组前r+1个元素不变，后面的元素从第w个元素开始替换到size-r长度结束，其中[w-size-r]元素的值分别对应着[elementData[r+1]-elementData[size-r]]的值
            System.arraycopy(elementData, r,
                             elementData, w,
                             size - r);
            w += size - r;
        }
        if (w != size) {
            // clear to let GC do its work
            for (int i = w; i < size; i++)
                elementData[i] = null;
            modCount += size - w;
            size = w;
            modified = true;
        }
    }
    return modified;
}
```

## 8.查找元素

### 8.1查找单个元素

```java
public int indexOf(Object o) {
    if (o == null) {
        for (int i = 0; i < size; i++)
            if (elementData[i]==null)
                return i;
    } else {
        for (int i = 0; i < size; i++)
            if (o.equals(elementData[i]))
                return i;
    }
    return -1;
}
```

### 8.2获取指定位置的元素

```java
public E get(int index) {
    rangeCheck(index);

    return elementData(index);
}
```

## 9.序列化

### 序列化集合

```java
//transient阻止序列化
transient Object[] elementData; 
private void writeObject(java.io.ObjectOutputStream s)
    throws java.io.IOException{
    // Write out element count, and any hidden stuff
    int expectedModCount = modCount;
    s.defaultWriteObject();

    // Write out size as capacity for behavioural compatibility with clone()
    s.writeInt(size);

    // Write out all elements in the proper order.
    for (int i=0; i<size; i++) {
        s.writeObject(elementData[i]);
    }

    if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
    }
}
```

### 反序列化

```java
private void readObject(java.io.ObjectInputStream s)
    throws java.io.IOException, ClassNotFoundException {
    elementData = EMPTY_ELEMENTDATA;

    // Read in size, and any hidden stuff
    s.defaultReadObject();

    // Read in capacity
    s.readInt(); // ignored

    if (size > 0) {
        ensureCapacityInternal(size);

        Object[] a = elementData;
        // Read in all elements in the proper order.
        for (int i=0; i<size; i++) {
            a[i] = s.readObject();
        }
    }
}
```

## 10.创建 Iterator 迭代器

```java
public Iterator<E> iterator() {
    return new Itr();
}
/**
 * 迭代器代码
 */
private class Itr implements Iterator<E> {
    //游标
    int cursor;       // index of next element to return
    int lastRet = -1; // index of last element returned; -1 if no such
    int expectedModCount = modCount;

    public boolean hasNext() {
        return cursor != size;
    }

    @SuppressWarnings("unchecked")
    public E next() {
        //<1> 校验expectedModCount = modCount是否相等
        checkForComodification();
        int i = cursor;
        if (i >= size)
            throw new NoSuchElementException();
        Object[] elementData = ArrayList.this.elementData;
        if (i >= elementData.length)
            throw new ConcurrentModificationException();
        cursor = i + 1;
        return (E) elementData[lastRet = i];
    }

    public void remove() {
        if (lastRet < 0)
            throw new IllegalStateException();
        checkForComodification();

        try {
            ArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
            expectedModCount = modCount;
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void forEachRemaining(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        final int size = ArrayList.this.size;
        int i = cursor;
        if (i >= size) {
            return;
        }
        final Object[] elementData = ArrayList.this.elementData;
        if (i >= elementData.length) {
            throw new ConcurrentModificationException();
        }
        while (i != size && modCount == expectedModCount) {
            consumer.accept((E) elementData[i++]);
        }
        // update once at end of iteration to reduce heap write traffic
        cursor = i;
        lastRet = i - 1;
        checkForComodification();
    }
    
    final void checkForComodification() {
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
    }
}
```

- `checkForComodification`比较`modCount != expectedModCount`值不相等抛出`ConcurrentModificationException`异常
- `#hasNext`方法判断游标有没有达到最后位置
- `#next`方法，每次移动一次，游标`cursor`+1
- `#remove`方法，会重新调用集合的`remove`方法，并且重新初始化`expectedModCount = modCount`，所以永迭代器遍历删除元素不会抛出`ConcurrentModificationException`异常

## 11.仿写`ArrayList`



## 小结

- `ArrayList` 是基于 `[]` 数组实现的 List 实现类，支持在数组容量不够时，一般按照 **1.5** 倍**自动**扩容。同时，它支持**手动**扩容、**手动**缩容。
- `ArrayList` 随机访问时间复杂度是 O(1) ，查找指定元素的**平均**时间复杂度是 O(n) 。
- `ArrayList` 移除指定位置的元素的最好时间复杂度是 O(1) ，最坏时间复杂度是 O(n) ，平均时间复杂度是 O(n) 
- `ArrayList` 移除指定元素的时间复杂度是 O(n) 。
- `ArrayList`添加元素的最好时间复杂度是 O(1) ，最坏时间复杂度是 O(n) ，平均时间复杂度是 O(n) 。



