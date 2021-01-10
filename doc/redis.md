## 1、Redis介绍

​	Redis 是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件。它支持多种类型的数据结构，如 [字符串（strings）](http://www.redis.cn/topics/data-types-intro.html#strings)， [散列（hashes）](http://www.redis.cn/topics/data-types-intro.html#hashes)， [列表（lists）](http://www.redis.cn/topics/data-types-intro.html#lists)， [集合（sets）](http://www.redis.cn/topics/data-types-intro.html#sets)， [有序集合（sorted sets）](http://www.redis.cn/topics/data-types-intro.html#sorted-sets) 与范围查询， [bitmaps](http://www.redis.cn/topics/data-types-intro.html#bitmaps)， [hyperloglogs](http://www.redis.cn/topics/data-types-intro.html#hyperloglogs) 和 [地理空间（geospatial）](http://www.redis.cn/commands/geoadd.html) 索引半径查询。 Redis 内置了 [复制（replication）](http://www.redis.cn/topics/replication.html)，[LUA脚本（Lua scripting）](http://www.redis.cn/commands/eval.html)， [LRU驱动事件（LRU eviction）](http://www.redis.cn/topics/lru-cache.html)，[事务（transactions）](http://www.redis.cn/topics/transactions.html) 和不同级别的 [磁盘持久化（persistence）](http://www.redis.cn/topics/persistence.html)， 并通过 [Redis哨兵（Sentinel）](http://www.redis.cn/topics/sentinel.html)和自动 [分区（Cluster）](http://www.redis.cn/topics/cluster-tutorial.html)提供高可用性（high availability）。

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







