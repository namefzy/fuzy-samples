## 1、Redis介绍

​	Redis 是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件。它支持多种类型的数据结构，如 [字符串（strings）](http://www.redis.cn/topics/data-types-intro.html#strings)， [散列（hashes）](http://www.redis.cn/topics/data-types-intro.html#hashes)， [列表（lists）](http://www.redis.cn/topics/data-types-intro.html#lists)， [集合（sets）](http://www.redis.cn/topics/data-types-intro.html#sets)， [有序集合（sorted sets）](http://www.redis.cn/topics/data-types-intro.html#sorted-sets) 与范围查询， [bitmaps](http://www.redis.cn/topics/data-types-intro.html#bitmaps)， [hyperloglogs](http://www.redis.cn/topics/data-types-intro.html#hyperloglogs) 和 [地理空间（geospatial）](http://www.redis.cn/commands/geoadd.html) 索引半径查询。 Redis 内置了 [复制（replication）](http://www.redis.cn/topics/replication.html)，[LUA脚本（Lua scripting）](http://www.redis.cn/commands/eval.html)， [LRU驱动事件（LRU eviction）](http://www.redis.cn/topics/lru-cache.html)，[事务（transactions）](http://www.redis.cn/topics/transactions.html) 和不同级别的 [磁盘持久化（persistence）](http://www.redis.cn/topics/persistence.html)， 并通过 [Redis哨兵（Sentinel）](http://www.redis.cn/topics/sentinel.html)和自动 [分区（Cluster）](http://www.redis.cn/topics/cluster-tutorial.html)提供高可用性（high availability）。Redis默认16个库，但它并没有完全隔离。

​	Redis的特点：单线程、内存结构、多路复用。

## 2、集群搭建

`redis`版本6.0.6，使用三台虚拟机，3主3从的配置，主机ip分别为`192.168.56.100`、`192.168.56.101`、`192.168.56.102`首先分别在对应的虚拟机上指定redis主从的配置文件：

以`192.168.56.100`服务器为例创建主从服务器：

```shell
cd /home/user/redis-6.0.6
mkdir redis-cluster
cd redis-cluster
mkdir 7291 7292
cp /home/user/redis-6.0.6/config/redis.conf /home/user/redis-6.0.6/redis-cluster/7291
cp /home/user/redis-6.0.6/config/redis.conf /home/user/redis-6.0.6/redis-cluster/7292
```

修改7291目录下配置文件`redis.config`

```shell
port 7291
daemonize yes
protected-mode no
dir /usr/local/soft/redis-5.0.5/redis-cluster/7291/
cluster-enabled yes
cluster-config-file nodes-7291.conf
cluster-node-timeout 5000
appendonly yes
pidfile /var/run/redis_7291.pid
```

注意如果外网搭建需要配置以下参数：

```shell
# 实际给各节点网卡分配的IP（公网IP）
cluster-announce-ip 47.xx.xx.xx
# 节点映射端口
cluster-announce-port ${PORT}
# 节点总线端口
cluster-announce-bus-port 1${PORT}
```

批量替换7292文件夹下的`redis.config`文件内容

```shell
cd /home/user/redis-6.0.6/redis-cluster
sed -i 's/7291/7292/g' 7292/redis.conf
```

服务器`192.168.56.101`、`192.168.56.102`也做类似的操作，然后启动三台服务器中所有的redis程序：

```shell
./src/redis-server redis-cluster/7291/redis.conf
./src/redis-server redis-cluster/7292/redis.conf
./src/redis-server redis-cluster/7293/redis.conf
./src/redis-server redis-cluster/7294/redis.conf
./src/redis-server redis-cluster/7295/redis.conf
./src/redis-server redis-cluster/7296/redis.conf
```

创建集群：

```txt
redis-cli --cluster create 192.168.56.100:7291 192.168.56.100:7292 192.168.56.102:7293 192.168.56.102:7294 192.168.56.101:7295 192.168.56.101:7296 --cluster-replicas 1
```

展示出如下信息，代表集群创建成功

```shell
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 192.168.56.102:7294 to 192.168.56.100:7291
Adding replica 192.168.56.101:7296 to 192.168.56.102:7293
Adding replica 192.168.56.100:7292 to 192.168.56.101:7295
M: effcab530dc2d822e2e1bde724f227e35aca3f56 192.168.56.100:7291
   slots:[0-5460] (5461 slots) master
S: fd992018f084900934ffec3162df583296ea8481 192.168.56.100:7292
   replicates 6b8dcbc04cf0caf87d214d97fca436fa00919dac
M: 4fc0f148f577365d15112bc9aa9667cbbfc5ff64 192.168.56.102:7293
   slots:[5461-10922] (5462 slots) master
S: 774c931349ce63df80e68fc47457c540bdf93079 192.168.56.102:7294
   replicates effcab530dc2d822e2e1bde724f227e35aca3f56
M: 6b8dcbc04cf0caf87d214d97fca436fa00919dac 192.168.56.101:7295
   slots:[10923-16383] (5461 slots) master
S: bbaeb5377b0dd1da3076661de84488cd316743a3 192.168.56.101:7296
   replicates 4fc0f148f577365d15112bc9aa9667cbbfc5ff64
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
.
>>> Performing Cluster Check (using node 192.168.56.100:7291)
M: effcab530dc2d822e2e1bde724f227e35aca3f56 192.168.56.100:7291
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
S: bbaeb5377b0dd1da3076661de84488cd316743a3 192.168.56.101:7296
   slots: (0 slots) slave
   replicates 4fc0f148f577365d15112bc9aa9667cbbfc5ff64
S: fd992018f084900934ffec3162df583296ea8481 192.168.56.100:7292
   slots: (0 slots) slave
   replicates 6b8dcbc04cf0caf87d214d97fca436fa00919dac
M: 4fc0f148f577365d15112bc9aa9667cbbfc5ff64 192.168.56.102:7293
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
S: 774c931349ce63df80e68fc47457c540bdf93079 192.168.56.102:7294
   slots: (0 slots) slave
   replicates effcab530dc2d822e2e1bde724f227e35aca3f56
M: 6b8dcbc04cf0caf87d214d97fca436fa00919dac 192.168.56.101:7295
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

### Hash槽分配

创建脚本文件：

```shell
#!/bin/bash
for ((i=0;i<20000;i++))
do
echo -en "helloworld" | redis-cli -h 192.168.8.207 -p 7291 -c -x set name$i >>redis.log
done
```

授权

```shell
chmod +x setkey.sh
./setkey.sh
```

进入redis客户端`redis-cli -p 端口`查看服务器上的节点数据：

```shell
127.0.0.1:7293> dbsize
(integer) 6683
127.0.0.1:7296> dbsize
(integer) 6665
127.0.0.1:7291> dbsize
(integer) 6652
```



## 2、Redis定位与特性

### 传统型关系型数据库

**优点**

- 它以表格的形式，基于行存储数据，是一个二维的模式。

- 它存储的是结构化的数据，数据存储有固定的模式（schema），数据需要适应表结构。

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

### String类型

- Redis中key和value的大小不能超过512M
- redis中的过期时间不会因为修改超过而被刷新，只有当执行`set del getset`命令时，命令会清除超时时间

#### 基本操作

```shell
> set mykey somevalue
OK
> get mykey 
"somevalue"

#设置带有过期时间的key
redis> SET key-with-expire-time "hello" EX 10086
OK
redis> GET key-with-expire-time
"hello"
redis> TTL key-with-expire-time
(integer) 10069

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

#### 存储实现原理

以`set hello world`为例，每个键值对都会有一个`dictEntry`，它的结构如下图：

![image-20210601222318969](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210601222318969.png)

如图所示，key存储在SDS中，value存储在`redisObject`对象中（五种常用的数据类型都是通过`redisObject`来存储的）

##### SDS

redis中存储字符串的一种实现，它有几种结构分别用于存储不同长度的字符串。

字符串类型的内部编码有三种：

> 1、int，存储 8 个字节的长整型（long，2^63-1）。 
>
> 2、embstr, 代表 embstr 格式的 SDS（Simple Dynamic String 简单动态字符串）， 存储小于 44 个字节的字符串。
>
>  3、raw，存储大于 44 个字节的字符串

#### 应用场景

- 热点数据缓存（例如报表，明星出轨），对象缓存，全页缓存。可以提升热点数据的访问速度。

- 分布式Session

  ```xml
  <dependency>
      <groupId>org.springframework.session</groupId>
      <artifactId>spring-session-data-redis</artifactId>
  </dependency>
  ```

- 分布式锁

  set key value EX 10 NX：如果key存在则设置失败，否则10m后key过期。

- 全局ID

  ```shell
  incrby userid 1000
  ```

- 计数器

### Hash类型

![image-20210111205330282](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210111205330282.png)

​																											**Hash结构**

**如图，Hash类型属于键值对类型，但value字段只能存储字符串，不能存储其他类型。**

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

String可以做的事情，Hash都可以做(Hash更节省空间，一个key就能存储一个对象的数据)。另外还有其他适用场景，**比如根据用户查询购物车信息**，我们可以设置以下结构：

key：用户 id；field：商品 id；value：商品数量。 

对应的操作可以设置以下命令操作来完成购物车操作：

- 购物车商品+1：hincr。
- 购物车商品-1：hdecr。
- 删除购物车：hdel。
- 全选：hgetall。
- 获取商品数量：hlen。

### List类型

> Redis lists基于LinkedList实现。也就是说该数据结构既有栈的特性也有队列的特性。
>

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

- 在博客引擎实现中，你可为每篇日志设置一个list，在该list中推入博客评论，等等。

#### List的阻塞操作

​	可以使用Redis来实现生产者和消费者模型，如使用LPUSH和RPOP来实现该功能。但会遇到这种情景：list是空，这时候消费者就需要轮询来获取数据，这样就会增加redis的访问压力、增加消费端的cpu时间，而很多访问都是无用的。为此redis提供了阻塞式访问 [BRPOP](http://www.redis.cn/commands/brpop.html) 和 [BLPOP](http://www.redis.cn/commands/blpop.html) 命令。 消费者可以在获取数据时指定如果数据不存在阻塞的时间，如果在时限内获得数据则立即返回，如果超时还没有数据则返回null, 0表示一直阻塞。**同时redis还会为所有阻塞的消费者以先后顺序排队。**

### Set类型

> Redis Set 是 String 的无序排列。`SADD` 指令把新的元素添加到 set 中。对 set 也可做一些其他的操作，比如测试一个给定的元素是否存在，对不同 set 取交集，并集或差，等等。**最大存储数量 2^32-1**

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

> ​	1、一次发送多个命令，减少网络开销。 
>
> ​	2、Redis 会将整个脚本作为一个整体执行，不会被其他请求打断，保持原子性。 
>
> ​	3、对于复杂的组合命令，我们可以放在文件中，可以实现程序之间的命令集复 用。

###  EVAL

#### 基本操作

**Lua脚本基本命令：`eval lua-script key-num [key1 key2 key3 ....] [value1 value2 value3 ....]`**

- eval 代表执行 Lua 语言的命令
- lua-script 代表 Lua 语言脚本内容
- key-num 表示参数中有多少个 key，需要注意的是 Redis 中 key 是从 1 开始的，如果没有 key 的参数，那么写 0。
- [key1 key2 key3…]是 key 作为参数传递给 Lua 语言，也可以不填，但是需要和 key-num 的个数对应起来。
- [value1 value2 value3 ….]这些参数传递给 Lua 语言，它们是可填可不填的。

```shell
127.0.0.1:6379> eval "return 'Hello World'" 0
"Hello World"
127.0.0.1:6379> eval "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}" 2 key1 key2 first second
1) "key1"
2) "key2"
3) "first"
4) "second"
```

#### Lua脚本在redis中操作

**Lua脚本中执行redis命令**

- `redis.call()`：执行命令过程中发生错误会停止脚本，并返回一个错误

  ```shell
  > eval "return redis.call('set',KEYS[1],'bar')" 1 foo
  OK
  ```

- `redis.pcall()`：出错时并不引发(raise)错误，而是返回一个带 `err` 域的 Lua 表(table)，用于表示错误

  ```shell
  > eval "return redis.pcall('set',KEYS[1],'bar')" 1 foo1
  OK
  ```

**Lua脚本**

`test.lua`

```lua
redis.call('set','lua','lua666')
return redis.call('get','lua')
```

`执行命令`

```shell
[root@localhost src]# redis-cli --eval ../lua/test.lua 0
"lua666"
```

[EVAL](http://redisdoc.com/script/eval.html#eval) 命令要求你在每次执行脚本的时候都发送一次脚本主体(script body)。Redis 有一个内部的缓存机制，因此它不会每次都重新编译脚本，不过在很多场合，付出无谓的带宽来传送脚本主体并不是最佳选择。

#### EVALSHA命令

`EVALSHA`命令的表现如下：

- 如果服务器还记得给定的 SHA1 校验和所指定的脚本，那么执行这个脚本
- 如果服务器不记得给定的 SHA1 校验和所指定的脚本，那么它返回一个特殊的错误，提醒用户使用 [EVAL](http://redisdoc.com/script/eval.html#eval) 代替 EVALSHA

以下是示例：

```shell
> set foo bar
OK
> eval "return redis.call('get','foo')" 0
"bar"
> evalsha 6b1bf486c81ceb7edf3c093f4c48582e38c0e791 0
"bar"
#缓存不存在
> evalsha ffffffffffffffffffffffffffffffffffffffff 0
(error) `NOSCRIPT` No matching script. Please use [EVAL](/commands/eval).
```

#### SCRIPT LOAD script

> 将脚本 `script` 添加到脚本缓存中，但并不立即执行这个脚本。[EVAL script numkeys key [key …\] arg [arg …]](http://redisdoc.com/script/eval.html#eval) 命令也会将脚本添加到脚本缓存中，但是它会立即对输入的脚本进行求值。

```shell
redis> SCRIPT LOAD "return 'hello moto'"
"232fd51614574cf0867b83d384a5e898cfd24e5a"
redis> EVALSHA 232fd51614574cf0867b83d384a5e898cfd24e5a 0
"hello moto"
```

#### SCRIPT EXISTS

> 给定一个或多个脚本的 SHA1 校验和，返回一个包含 `0` 和 `1` 的列表，表示校验和所指定的脚本是否已经被保存在缓存当中。
>
> ## 返回值
>
> 一个列表，包含 `0` 和 `1` ，前者表示脚本不存在于缓存，后者表示脚本已经在缓存里面了。 列表中的元素和给定的 SHA1 校验和保持对应关系，比如列表的第三个元素的值就表示第三个 SHA1 校验和所指定的脚本在缓存中的状态。

```shell
redis> SCRIPT LOAD "return 'hello moto'"    # 载入一个脚本
"232fd51614574cf0867b83d384a5e898cfd24e5a"

redis> SCRIPT EXISTS 232fd51614574cf0867b83d384a5e898cfd24e5a
1) (integer) 1
redis> SCRIPT FLUSH     # 清空缓存
OK
redis> SCRIPT EXISTS 232fd51614574cf0867b83d384a5e898cfd24e5a
1) (integer) 0
```

#### SCRIPT KILL

> 杀死当前正在运行的 Lua 脚本，当且仅当这个脚本没有执行过任何写操作时，这个命令才生效。这个命令主要用于终止运行时间过长的脚本，比如一个因为 BUG 而发生无限 loop 的脚本，诸如此类。

```shell
# 没有脚本在执行时
redis> SCRIPT KILL
(error) ERR No scripts in execution right now.
# 成功杀死脚本时
redis> SCRIPT KILL
OK
(1.30s)
# 尝试杀死一个已经执行过写操作的脚本，失败
redis> SCRIPT KILL
(error) ERR Sorry the script already executed write commands against the dataset. You can either wait the script termination or kill the server in an hard way using the SHUTDOWN NOSAVE command.
(1.69s)
```

## 6、过期策略

### 定时过期

> 每个设置过期时间的 key 都需要创建一个定时器，到过期时间就会立即清除。该策 略可以立即清除过期的数据，对内存很友好；但是会占用大量的 CPU 资源去处理过期的 数据，从而影响缓存的响应时间和吞吐量。

### 惰性过期

> 只有当访问一个 key 时，才会判断该 key 是否已过期，过期则清除。该策略可以最 大化地节省 CPU 资源，却对内存非常不友好。极端情况可能出现大量的过期 key 没有再 次被访问，从而不会被清除，占用大量内存。

### 淘汰机制

> Redis 的内存淘汰策略，是指当内存使用达到最大内存极限时，需要使用淘汰算法来 决定清理掉哪些数据，以保证新数据的存入。

**最大内存配置**

`redis.config`参数配置

```shell
# maxmemory <bytes>
```

**淘汰算法**

- LRU(Least Recently Used)：最近最少使用。判断最近被使用的时间，目前最远的 数据优先被淘汰。
- LFU(Least Frequently Used)：最不常用。

**淘汰策略**

| 策略              | 含义                                                         |
| ----------------- | ------------------------------------------------------------ |
| `volatile-lru`    | 根据LRU(最近最少使用) 算法删除设置了超时属性的键，直到腾出足够内存为止。如果没有 可删除的键对象，回退到 noeviction 策略。 |
| `allkeys-lru`     | 根据 LRU 算法删除键，不管数据有没有设置超时属性，直到腾出足够内存为止。 |
| `volatile-lfu`    | 在带有过期时间的键中选择最不常用的。                         |
| `allkeys-lfu`     | 在所有的键中选择最不常用的，不管数据有没有设置超时属性。     |
| `volatile-random` | 在带有过期时间的键中随机选择。                               |
| `allkeys-random`  | 随机删除所有键，直到腾出足够内存为止。                       |
| `volatile-ttl`    | 根据键值对象的 ttl 属性，删除最近将要过期数据。如果没有，回退到 noeviction 策略。 |
| **`noeviction`**  | **默认策略**，不会删除任何数据，拒绝所有写入操作并返回客户端错误信息（error）OOM command not allowed when used memory，此时 Redis 只响应读操作。 |

**redis.config策略配置**

```shell
#maxmemory-policy noeviction
```

## 7、Redis持久化机制

> ​	Redis 速度快，很大一部分原因是因为它所有的数据都存储在内存中。如果断电或者 宕机，都会导致内存中的数据丢失。为了实现重启后数据不丢失，Redis 提供了两种持久 化的方案，一种是 RDB 快照（Redis DataBase），一种是 AOF（Append Only File）。

### 7、1 RDB(Redis DataBase-快照)

​	`RDB`是Redis默认的持久化方案。当满足一定条件时，会把当前内存中的数据写入磁盘，生成一个快照文件`dump.rdb`。`Redis`重启会通过加载`dump.rdb`文件恢复数据。**那么什么时候会写入rdb文件？**

#### 自动触发

- 配置规则触发

  ```shell
  #redis.conf配置文件里面定义了触发频率，如下；这些配置是不冲突的，满足任意一个条件就会触发
  save 900 1 # 900 秒内至少有一个 key 被修改（包括添加）
  save 300 10 # 400 秒内至少有 10 个 key 被修改
  save 60 10000 # 60 秒内至少有 10000 个 key 被修改
  
  #rdb相关配置
  dir ./# 文件路径
  dbfilename dump.rdb# 文件名称
  rdbcompression yes# 是否是 LZF 压缩 rdb 文件
  rdbchecksum yes# 开启数据校验
  ```

- `shutdown`命令触发

- `flushall`命令触发

#### 手动触发

​	如果我们需要重启服务或者迁移数据，这个时候就需要手动触发 RDB 快照。

- `save`

  > save 在生成快照的时候会阻塞当前 Redis 服务器， Redis 不能处理其他命令。如果 内存中的数据比较多，会造成 Redis 长时间的阻塞。生产环境不建议使用这个命令。

- `bgsave`

  > 执行 bgsave 时，Redis 会在后台异步进行快照操作，快照同时还可以响应客户端请 求。
  >
  > 具体操作是 Redis 进程执行 fork 操作创建子进程（copy-on-write），RDB 持久化 过程由子进程负责，完成后自动结束。它不会记录 fork 之后后续的命令。阻塞只发生在 fork 阶段，一般时间很短。 
  >
  > 用 lastsave 命令可以查看最近一次成功生成快照的时间。

### 7、2 AOF(Append Only File)

> redis默认不开启该持久化机制。AOF采用日志的形式记录每个命令操作，并追加到文件中。开启后，执行更改redis数据的命令时，就会把命令写入到AOF文件中。

**AOF配置**

```shell
# 开关
appendonly no
# 文件名
appendfilename 
```

- 数据都是实时持久化到磁盘？

  由于操作系统的缓存机制，AOF 数据并没有真正地写入硬盘，而是进入了系统的硬盘缓存。

- 什么时候把缓冲区的文件写入到AOF文件？

  | 参数                   | 说明                                                         |
  | ---------------------- | ------------------------------------------------------------ |
  | `appendfsync everysec` | AOF 持久化策略（硬盘缓存到磁盘），默认 everysec                                                                                                                                      1：no 表示不执行 fsync，由操作系统保证数据同步到磁盘，速度最快，但是不太安全；                                                                                2：always 表示每次写入都执行 fsync，以保证数据同步到磁盘，效率很低；                                                                                              3：everysec 表示每秒执行一次 fsync，可能会导致丢失这 1s 数据。通常选择 everysec ， 兼顾安全性和效率。 |

- 文件过大怎么办？

  redis新增了重写机制，当 AOF 文件的大小超过所设定的阈值 时，Redis 就会启动 AOF 文件的内容压缩，只保留可以恢复数据的最小指令集。**可以使用命令 bgrewriteaof 来重写。**

- **重写触发策略**

  ```shell
  # 重写触发机制
  auto-aof-rewrite-percentage 100
  auto-aof-rewrite-min-size 64mb
  ```

### 7、3 AOF与RDB比较

| 持久化机制 | 优点                                                         | 缺点                                                         |
| ---------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `RDB`      | 1.RDB 是一个非常紧凑(compact)的文件，它保存了 redis 在某个时间点上的数据 集。这种文件非常适合用于进行备份和灾难恢复;2.生成 RDB 文件的时候，redis 主进程会 fork()一个子进程来处理所有保存工作，主 进程不需要进行任何磁盘 IO 操作。                                                                 3.RDB 在恢复大数据集时的速度比 AOF 的恢复速度要快。 | 1、RDB 方式数据没办法做到实时持久化/秒级持久化。因为 bgsave 每次运行都要 执行 fork 操作创建子进程，频繁执行成本过高;2在一定间隔时间做一次备份，所以如果 redis 意外 down 掉的话，就会丢失最后 一次快照之后的所有修改（数据有丢失）。 |
| `AOF`      | AOF 持久化的方法提供了多种的同步频率，即使使用默认的同步频率每秒同步 一次，Redis 最多也就丢失 1 秒的数据而已 | 1、对于具有相同数据的的 Redis，AOF 文件通常会比 RDF 文件体积更大（RDB 存的是数据快照）。 2、虽然 AOF 提供了多种同步的频率，默认情况下，每秒同步一次的频率也具有较 高的性能。在高并发的情况下，RDB 比 AOF 具好更好的性能保证 |

### 7、4 对于持久化方案选择

​	如果可以忍受一小段时间内数据的丢失，毫无疑问使用 RDB 是最好的，定时生成 RDB 快照（snapshot）非常便于进行数据库备份， 并且 RDB 恢复数据集的速度也要 比 AOF 恢复的速度要快。 否则就使用 AOF 重写。

​	但是一般情况下建议不要单独使用某一种持久化机制，而 是应该两种一起用，在这种情况下,当 redis 重启的时候会优先载入 AOF 文件来恢复原始 的数据，因为在通常情况下 AOF 文件保存的数据集要比 RDB完整。

## 8、Redis高可用

### 8、1 主从复制

​	和Mysql主从复制的原因一样，Redis虽然读取写入的速度都特别快，但是也会产生读压力特别大的情况。为了分担读压力，Redis支持主从复制，Redis的主从结构可以采用一主多从或者级联结构，Redis主从复制可以根据是否是全量分为全量同步和增量同步。**Redis从节点只能读。**主从复制结构图如下：

![redis主从复制](https://image-1301573777.cos.ap-chengdu.myqcloud.com/redis主从复制.png)

​																													**redis主从结构**

**全量同步流程**

![redis主从复制-全量同步](https://image-1301573777.cos.ap-chengdu.myqcloud.com/redis主从复制-全量同步.png)

​																												**redis全量同步**

**主从复制的不足**

- RDB文件过大，同步非常耗时
- 在一主多从的情况下，如果主服务器挂了，对外提供的服务器就无法使用。如果每次都是手动把之前的从服务器切换成主服务器， 这个比较费时费力，还会造成一定时间的服务不可用。

[redis主从复制](https://www.cnblogs.com/daofaziran/p/10978628.html)

### 8、2 Sentinel

#### Sentinel作用

Redis 的`Sentinel`系统用于管理多个 Redis 服务器（instance）， 该系统执行以下三个任务：

- **监控（Monitoring）**： Sentinel 会不断地检查你的主服务器和从服务器是否运作正常。
- **提醒（Notification）**： 当被监控的某个 Redis 服务器出现问题时， Sentinel 可以通过 API 向管理员或者其他应用程序发送通知。
- **自动故障迁移（Automatic failover）**： 当一个主服务器不能正常工作时， Sentinel 会开始一次自动故障迁移操作， 它会将失效主服务器的其中一个从服务器升级为新的主服务器， 并让失效主服务器的其他从服务器改为复制新的主服务器； 当客户端试图连接失效的主服务器时， 集群也会向客户端返回新主服务器的地址， 使得集群可以使用新主服务器代替失效服务器。

为了保证监控服务器的可用性，我们会对 Sentinel 做集群的部署。Sentinel 既监控 所有的 Redis 服务，Sentinel 之间也相互监控，结构图如下：

![image-20210117142057293](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210117142057293.png)

​																															**redis-sentinel集群**

#### 服务下线

Redis 的 Sentinel 中关于下线（down）有两个不同的概念：

- 主观下线（Subjectively Down， 简称 SDOWN）指的是单个 Sentinel 实例对服务器做出的下线判断。
- 客观下线（Objectively Down， 简称 ODOWN）指的是多个 Sentinel 实例在对同一个服务器做出 SDOWN 判断， 并且通过 `SENTINEL is-master-down-by-addr` 命令互相交流之后， 得出的服务器下线判断。 （一个 Sentinel 可以通过向另一个 Sentinel 发送 `SENTINEL is-master-down-by-addr` 命令来询问对方是否认为给定的服务器已下线。）

#### 故障转移

​	如果`master`被标记为下线，就会开始故障转移流程。那么在`sentinel集群`中谁来做故障转移这件事？故障转移流程的第一步就是在`Sentinel`集群选择一个 Leader，由 Leader 完成故障 转移流程。Sentinle通过[Raft](http://thesecretlivesofdata.com/raft/)算法，实现 Sentinel 选举，其中`raft`算法的核心思想就是**少数服从多数**，所以在配置服务器的时候总数为奇数最好。

### 8、3 `Redis Cluster`

​	`Redis Cluster`可以看成是由多个`Redis`实例组成的数据集合。客户端不需要关注数据的子集到底存储在哪个节点，只需要关注这个集合整体。它是一个去中心化的结构。已3主3从为例：

![image-20210117205709847](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210117205709847.png)

[`redis cluster`集群搭建](https://gper.club/articles/7e7e7f7ff7g5egc7g6d)

#### 数据分布

​	redis没有使用**哈希取模**，也没有使用[一致性哈希](https://www.jianshu.com/p/528ce5cd7e8f)算法，而是采用对key进行**CRC16**算法计算再%槽位个数（16384），得到的一个slot的值，数据则落在这个槽上。key和每个槽位的的关系是永远不会变的。

![image-20210117210303614](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210117210303614.png)

​																													**redis槽位计算**

#### 客户端重定向

```shell
#服务端返回 MOVED，也就是根据 key 计算出来的 slot 不归 7005端口管理，而是 归7004端口管理，服务端返回 MOVED 告诉客户端去7004端口操作。
127.0.0.1:7005> set jeck 5
(error) MOVED 598 192.168.56.101:7004
```

Jedis 等客户端会在本地维护一份 slot——node 的映射关系，大部分时候不需要重 定向，所以叫做 smart jedis（需要客户端支持）。 

#### 数据迁移

​	因为 key 和 slot 的关系是永远不会变的，当新增了节点的时候，需要把原有的 slot 分配给新的节点负责，并且把相关的数据迁移过来。

#### 集群的优缺点

**优点**

- 无中心架构
- 数据按照 slot 存储分布在多个节点，节点间数据共享，可动态调整数据分布。
- 可扩展性，可线性扩展到 1000 个节点（官方推荐不超过 1000 个），节点可动 态添加或删除。
- 高可用性，部分节点不可用时，集群仍可用。通过增加 Slave 做 standby 数据副 本，能够实现故障自动 failover，节点之间通过 gossip 协议交换状态信息，用投票机制 完成 Slave 到 Master 的角色提升。
- 降低运维成本，提高系统的扩展性和可用性。

**缺点**

- Client 实现复杂，驱动要求实现 Smart Client，缓存 slots mapping 信息并及时 更新，提高了开发难度，客户端的不成熟影响业务的稳定性。
- 节点会因为某些原因发生阻塞（阻塞时间大于 clutser-node-timeout），被判断 下线，这种 failover 是没有必要的。
- 数据通过异步复制，不保证数据的强一致性。
- 多个业务使用同一套集群时，无法根据统计区分冷热数据，资源隔离性较差，容 易出现相互影响的情况。

### 8、4 集群实现高可用的原理

在`redis cluster`中，什么时候集群不可用，什么时候在主节点挂掉会选举`master`节点？

**整个集群进入fail状态的必要条件**

- 某个主节点和所有从节点全部挂掉，我们集群就进入faill状态。
- 如果集群超过半数以上master挂掉，无论是否有slave，集群进入fail状态
- 如果集群任意master挂掉,且当前master没有slave.集群进入fail状态

**redis投票机制**

- slave发现自己的master变为FAIL
- 发起选举前，slave先给自己的`currentEpoch`加1，然后请求其它master给自己投票。slave是通过广播FAILOVER_AUTH_REQUEST包给集中的每个masters。**currentEpoch 这是一个集群状态相关的概念，可以当作记录集群状态变更的递增版本号。每个集群节点，都会通过server.cluster->currentEpoch记录当前的currentEpoch。集群节点创建时，不管是master还是slave，都置currentEpoch为0.当前节点接收到来自其他节点的包时，如果发送者的currentEpoch大于当前节点会更新currentEpoch为发送者的currentEpoch。因此，集群的所有节点从currentEpoch最终会达成一致，相当于对集群状态的认知达成了一致。**
- slave发起投票后，会等待至少两倍NODE_TIMEOUT时长接收自己投票结果，不管NODE_TIMEOUT何值，也至少会等待2秒。
- master接收投票后给slave响应FAILOVER_AUTH_ACK，并且在NODE_TIMEOUT*2 时间内只会给挂掉的集群节点中的一个slave节点投票。
- 如果slave收到FAILOVER_AUTH_ACK响应的epoch值小于自己的epoch，则会直接丢弃。以但slave收到多数master的FAILOVER_AUTH_ACK则声明自己赢得选举。
- 如果slave在两倍的NODE_TIMEOUT时间内至少2秒未赢得选举，则放弃本次选举，然后4倍NODE_TIMEOUT时间发起再次选举

## Redis常见面试题

### 数据一致性

​	我们在使用redis作缓存时，通常都是以下结构，但是该结构不能保证一致性，一个是数据库，一个是redis，当数据需要更新时，redis如何能保证获取最新的正确数据？

![image-20210119211522525](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210119211522525.png)

- 先更新数据库，再删除缓存

  更新数据库后，删除缓存由于某种原因没执行，则下次查的依旧是旧数据。**解决方案提供一种重试机制，例如将异常捕获然后将key加入消息队列，再次消费该消息；或者监听数据库binlog机制，当数据发生改变更新key。**

- 先删除缓存，在更新数据库

  1 ）线程 A 需要更新数据，首先删除了 Redis 缓存 

  2）线程 B 查询数据，发现缓存不存在，到数据库查询旧值，写入 Redis，返回 

  3）线程 A 更新了数据库

  解决方案：该方案对于B线程依然能查出旧数据。

  1）删除缓存 

  2）更新数据库 

  3）休眠 500ms（这个时间，依据读取数据的耗时而定）

   4）再次删除缓存

### 缓存雪崩

> **缓存雪崩就是 Redis 的大量热点数据同时过期（失效），因为设置了相同的过期时 间，刚好这个时候 Redis 请求的并发量又很大，就会导致所有的请求落到数据库。**

解决方案

- 缓存定时预先更新，避免同时失效
- 通过加随机数，使 key 在不同的时间过期

### 缓存穿透

> **当查询不存在redis中的key时，自然就会落到数据库的查询上了，此时用户恶意请求可能会导致一直访问数据库，给数据库带来过大压力。未命中redis中的key则就是缓存穿透。**

解决方案：

- 针对不存在的key缓存空数据

那么当用户恶意访问不断生成不同的key，来进行恶意访问数据呢？此时就会使用到**布隆过滤器**。

### 布隆过滤器

> 直观的说，bloom算法类似一个hash set，用来判断某个元素（key）是否在某个集合中。**它的优点是空间效率和查询时间都比一般的算法要好的多，缺点是有一定的误识别率和删除困难**。

回到**缓存穿透**中，我们可以将所有的key存入到布隆过滤器中。当用户采用各种各样的方式想越过缓存直接访问数据库时，我们首先进行布隆过滤器过滤掉不合法的key(存在一定的误判率)，不合法的key直接return，合法的key则查询redis返回数据；存在误判的情况，但此时已经被过滤掉绝大部分数据，可能少量数据依然会走数据库，但这能把数据库的压力降到最低。

[布隆过滤器](https://zhuanlan.zhihu.com/p/94433082)

### 实现一个lru算法

分析：

- 需要构造一个队列
- 将新增加的数据放入头节点
- 队列满了则将队列尾数据删除

自定义节点实现

```java
public class LRUCache {
    // 双向链表节点定义
    class Node {
        int key;
        int val;
        Node prev;
        Node next;
    }

    private int capacity;
    private Node first;
    private Node last;

    private Map<Integer,Node> map;

    public LRUCache1(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
    }

    public int get(int key){
        Node node = map.get(key);
        if(node==null){
            return -1;
        }
        moveToHead(node);
        return node.val;
    }

    private void moveToHead(Node node) {
        if(node == first){
            return;
        }else if(node==last){
            last.prev.next = null;
            last = last.prev;
        }else{
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        node.prev = first.prev;
        node.next =first;
        first.prev = node;
        first = node;
    }

    public void put(int key,int value){
        Node node = map.get(key);
        if(node == null){
            node = new Node();
            node.key = key;
            node.val = value;
            if(map.size()==capacity){
                removeLast();
            }
            addToHead(node);
            map.put(key, node);
        }else{
            node.val = value;
            moveToHead(node);
        }
    }
    private void addToHead(Node node) {
        if (map.isEmpty()) {
            first = node;
            last = node;
        } else {
            node.next = first;
            first.prev = node;
            first = node;
        }
    }

    private void removeLast() {
        map.remove(last.key);
        Node prevNode = last.prev;
        if (prevNode != null) {
            prevNode.next = null;
            last = prevNode;
        }
    }
}

```

基于`LinkedHashMap`实现

```java
public class LRUCache<K,V> extends LinkedHashMap<K,V> {

    //设定最大缓存空间 MAX_ENTRIES = 3;
    private static final int MAX_ENTRIES = 3;

    public LRUCache(){
        super(MAX_ENTRIES,0.75f,true);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size()>MAX_ENTRIES;
    }

    public static void main(String[] args){
        LRUCache<Integer, Integer> cache = new LRUCache<>();
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.get(1);
        cache.put(4, 4);
        System.out.println(cache.keySet());
    }
}
```

[redis文档](http://redisdoc.com/)











​	





​	

​	





























