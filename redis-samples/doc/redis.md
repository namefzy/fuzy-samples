## 1、Redis介绍

​	Redis 是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件。它支持多种类型的数据结构，如 [字符串（strings）](http://www.redis.cn/topics/data-types-intro.html#strings)， [散列（hashes）](http://www.redis.cn/topics/data-types-intro.html#hashes)， [列表（lists）](http://www.redis.cn/topics/data-types-intro.html#lists)， [集合（sets）](http://www.redis.cn/topics/data-types-intro.html#sets)， [有序集合（sorted sets）](http://www.redis.cn/topics/data-types-intro.html#sorted-sets) 与范围查询， [bitmaps](http://www.redis.cn/topics/data-types-intro.html#bitmaps)， [hyperloglogs](http://www.redis.cn/topics/data-types-intro.html#hyperloglogs) 和 [地理空间（geospatial）](http://www.redis.cn/commands/geoadd.html) 索引半径查询。 Redis 内置了 [复制（replication）](http://www.redis.cn/topics/replication.html)，[LUA脚本（Lua scripting）](http://www.redis.cn/commands/eval.html)， [LRU驱动事件（LRU eviction）](http://www.redis.cn/topics/lru-cache.html)，[事务（transactions）](http://www.redis.cn/topics/transactions.html) 和不同级别的 [磁盘持久化（persistence）](http://www.redis.cn/topics/persistence.html)， 并通过 [Redis哨兵（Sentinel）](http://www.redis.cn/topics/sentinel.html)和自动 [分区（Cluster）](http://www.redis.cn/topics/cluster-tutorial.html)提供高可用性（high availability）。Redis默认16个库，但它并没有完全隔离。

​	**Redis 是字典结构的存储方式，采用 key-value 存储。key 和 value 的最大长度限制 是 512M**

## 2、Redis定位与特性

### 传统型关系型数据库

**优点**

- 它以表格的形式，基于行存储数据，是一个二维的模式。

- 它存储的是结构化的数据，数据存储有固定的模式（schema），数据需要适应 表结构。

- 表与表之间存在关联（Relationship）。 

- 大部分关系型数据库都支持 SQL（结构化查询语言）的操作，支持复杂的关联查 询。 

- 通过支持事务（ACID 酸）来提供严格或者实时的数据一致性。

**缺点**

- 要实现扩容的话，只能向上（垂直）扩展，比如磁盘限制了数据的存储，就要扩 大磁盘容量，通过堆硬件的方式，不支持动态的扩缩容。水平扩容需要复杂的技术来实 现，比如分库分表。
- 表结构修改困难，因此存储的数据格式也受到限制。
- 在高并发和高数据量的情况下，我们的关系型数据库通常会把数据持久化到磁盘， 基于磁盘的读写压力比较大。

### NoSQL数据库

- 存储非结构化的数据，比如文本、图片、音频、视频。
- 表与表之间没有关联，可扩展性强。
- 保证数据的最终一致性。遵循 BASE（碱）理论。 Basically Available（基本 可用）； Soft-state（软状态）； Eventually Consistent（最终一致性）。
- 支持海量数据的存储和高并发的高效读写。
- 支持分布式，能够对数据进行分片存储，扩缩容简单。

## 3、Redis安装与启动

### 安装错误

[You need tcl 8.5 or newer in order to run the Redis test](https://blog.csdn.net/zhangchaoyang/article/details/104456978)

参考链接：

[reids6.0安装步骤](http://www.redis.cn/download.html)

[redis安装](https://gper.club/articles/7e7e7f7ff7g5egc4g6b)

[redis设置后台启动](https://www.cnblogs.com/glf1160/p/10618233.html)

## 4、Redis基本数据类型

- redis中的过期时间不会因为修改超过而被刷新，只有当执行`set del getset`命令时，命令会清除超时时间

### String类型

- Redis中key和value的大小不能超过512M

#### 基本操作

```shell
> set mykey somevalue
OK
> get mykey 
"somevalue"

#key不存在时会成功
> set mykey newval nx
(nil)
> set mykey newval xx
OK

#字符串自增 INCR 命令将字符串值解析成整型，将其加一，最后将结果保存为新的字符串值；它是线程安全的
> set counter 100
OK
> incr counter
(integer) 101
```

#### 应用场景

- 热点数据缓存（例如报表，明星出轨），对象缓存，全页缓存。可以提升热点数据的访问速度。
- 分布式Session
- 分布式锁
- 全局ID
- 计数器

### Hash类型

- 键值对个数最多为2^32-1个，也就是4294967295个

![image-20210111205330282](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210111205330282.png)

​																											**Hash结构**

如图，Hash类型属于键值对类型，但value字段只能存储字符串，不能存储其他类型。

#### 基本操作

```shell
#语法 hmset key field value field1 value1 [...]
> hmset user:1000 username antirez birthyear 1977 verified 1
OK
> hget user:1000 username
"antirez"
> hget user:1000 birthyear
"1977"
> hgetall user:1000
1) "username"
2) "antirez"
3) "birthyear"
4) "1977"
5) "verified"
6) "1"
```

#### 应用场景

String可以做的事情，Hash都可以做。另外还有其他适用场景，**比如根据用户查询购物车信息**，我们可以设置以下结构：

key：用户 id；field：商品 id；value：商品数量。 

对应的操作可以设置以下命令操作来完成购物车操作：

- 购物车商品+1：hincr。
- 购物车商品-1：hdecr。
- 删除购物车：hdel。
- 全选：hgetall。
- 获取商品数量：hlen。

### List类型

> Redis lists基于Linked Lists实现。也就是说该数据结构既有栈的特性也有队列的特性。
>
> list的元素个数最多为2^32-1个，也就是4294967295个。

#### 基本操作

```shell
#lrange 命令：lrange key start end:start和end都可以为负数，这是告知redis从尾部开始计数；
> rpush mylist A
(integer) 1
> rpush mylist B
(integer) 2
> lpush mylist first
(integer) 3
> lrange mylist 0 -1
1) "first"
2) "A"
3) "B"

> rpush mylist a b c
(integer) 3
> rpop mylist
"c"
> rpop mylist
"b"
> rpop mylist
"a"
```

#### 常用场景

- 例如在评级系统中，比如社会化新闻网站 reddit.com，你可以把每个新提交的链接添加到一个list，用LRANGE可简单的对结果分页。
- 在博客引擎实现中，你可为每篇日志设置一个list，在该list中推入博客评论，等等。

#### List的阻塞操作

​	可以使用Redis来实现生产者和消费者模型，如使用LPUSH和RPOP来实现该功能。但会遇到这种情景：list是空，这时候消费者就需要轮询来获取数据，这样就会增加redis的访问压力、增加消费端的cpu时间，而很多访问都是无用的。为此redis提供了阻塞式访问 [BRPOP](http://www.redis.cn/commands/brpop.html) 和 [BLPOP](http://www.redis.cn/commands/blpop.html) 命令。 消费者可以在获取数据时指定如果数据不存在阻塞的时间，如果在时限内获得数据则立即返回，如果超时还没有数据则返回null, 0表示一直阻塞。**同时redis还会为所有阻塞的消费者以先后顺序排队。**

### Set类型

> Redis Set 是 String 的无序排列。`SADD` 指令把新的元素添加到 set 中。对 set 也可做一些其他的操作，比如测试一个给定的元素是否存在，对不同 set 取交集，并集或差，等等。

#### 基本操作

- 添加一个或者多个元素：sadd myset a b c d e f g 
- 获取所有元素：smembers myset 
- 统计元素个数：scard myset 
- 随机获取一个元素 srandmember key 
- 随机弹出一个元素 spop myset 
- 移除一个或者多个元素 srem myset d e f 
- 查看元素是否存在 sismember myset a

```shell
> sadd myset 1 2 3
(integer) 3
> smembers myset
1. 3
2. 1
3. 2
#检测元素是否存在
> sismember myset 3
(integer) 1

```

#### 应用场景

**示例一：微博点赞**

> 用 like:t1001 来维护 t1001 这条微博的所有点赞用户
>
> 点赞了这条微博：sadd like:t1001 u3001 
>
> 取消点赞：srem like:t1001 u3001 
>
> 是否点赞：sismember like:t1001 u3001 
>
> 点赞的所有用户：smembers like:t1001 
>
> 点赞数：scard like:t1001

**示例二：商品打标签**

> 用 tags:i5001 来维护商品所有的标签
>
> sadd tags:i5001 画面清晰细腻 
>
> sadd tags:i5001 真彩清晰显示屏 
>
> sadd tags:i5001 流畅至极

**示例三：商品筛选**

> 获取差集：sdiff set1 set2
>
> 获取交集：sinter set1 set2
>
> 获取并集：sunion set1 set2

### ZSet类型

> sorted set，有序的 set，每个元素有个 score；score 相同时，按照 key 的 ASCII 码排序。

#### 基本操作

- 添加元素：zadd myzset 10 java 20 php 30 ruby 40 cpp 50 python
- 获取全部元素：zrange myzset 0 -1 withscores || zrevrange myzset 0 -1 withscores
- 根据分值区间获取元素：zrangebyscore myzset 20 30
- 移除元素：zrem myzset php cpp
- 统计元素个数：zcard myzset
- 获取元素：zsocre myzset java

#### 应用场景

​	id 为 6001 的新闻点击数加 1：zincrby hotNews:20190926 1 n6001 

​	获取今天点击最多的 15 条：zrevrange hotNews:20190926 0 15 withscores

### BitMaps

​	`BitMaps`最大的优点之一是，在存储信息时，它们通常可以极大地节省空间。例如，在用增量用户id表示不同用户的系统中，仅使用512MB内存就可以记住40亿用户的一位信息。**通常应用在用户访问统计和在线用户统计这块。**

### Hyperloglogs

​	提供了一种不太准确的基数统计方法，比如统计网站的 UV，存在 一定的误差。

### Streams

​	5.0 推出的数据类型。支持多播的可持久化的消息队列，用于实现发布订阅功能，借 鉴了 kafka 的设计。

## 5、Lua脚本





























