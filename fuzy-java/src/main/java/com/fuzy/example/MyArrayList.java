package com.fuzy.example;

import java.util.*;

/**
 * @ClassName MyArrayList
 * @Description TODO
 * @Author 11564
 * @Date 2020/10/15 21:27
 * @Version 1.0.0
 */
public class MyArrayList<E> extends AbstractList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    private static final Object[] EMPTY_ELEMENTDATA = {};

    transient Object[] elementData;

    private int size;

    public MyArrayList(){
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    @Override
    public boolean add(E e){
        ensureCapacityInternal(size+1);
        elementData[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o){
        if (o == null) {
            for (int index = 0; index < size; index++){
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
            }
        } else {
            for (int index = 0; index < size; index++){
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int indexOf(Object o){
        if (o == null) {
            for (int i = 0; i < size; i++){
                if (elementData[i]==null){
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++){
                if (o.equals(elementData[i])){
                    return i;
                }
            }
        }
        return -1;
    }

    private void fastRemove(int index) {
        modCount++;
        int numMoved = size-index-1;
        if(numMoved>0){
            System.arraycopy(elementData,index+1,elementData,index,numMoved);
        }
        elementData[--size] = null;
    }

    private void ensureCapacityInternal(int minCapacity) {
        //无参构造第一次添加元素初始化数组大小
        if(elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA){
            minCapacity = Math.max(DEFAULT_CAPACITY,minCapacity);
        }
        ensureExplicitCapacity(minCapacity);
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;
        if(minCapacity-elementData.length>0){
            grow(minCapacity);
        }
    }
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity+(oldCapacity<<1);
        if(newCapacity-minCapacity<0){
            newCapacity = minCapacity;
        }
        if(newCapacity - MAX_ARRAY_SIZE>0){
            newCapacity = hugeCapacity(minCapacity);
        }
        elementData = Arrays.copyOf(elementData,newCapacity);
    }

    private static int hugeCapacity(int minCapacity){
        if (minCapacity < 0){
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }
    @Override
    public E get(int index) {
        return (E) elementData[index];
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }
    private class Itr implements Iterator<E>{

        int lastRet = -1;

        int expectedModCount = modCount;

        int cursor;

        @Override
        public boolean hasNext() {
            return cursor!=size;
        }

        @Override
        public E next() {
            checkForComodification();

            int i = cursor;
            if(i>size){
                throw new NoSuchElementException();
            }
            Object[] elementData = MyArrayList.this.elementData;
            if(i>=elementData.length){
                throw new ConcurrentModificationException();
            }
            cursor = i+1;
            return (E) elementData[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0){
                throw new IllegalStateException();
            }
            checkForComodification();

            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
        final void checkForComodification() {
            if(modCount!=expectedModCount){
                throw new ConcurrentModificationException();
            }
        }
    }

}
