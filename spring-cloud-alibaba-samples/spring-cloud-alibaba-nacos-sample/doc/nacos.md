# Nacos

## 1、什么是nacos

​	微服务体系中多个服务集群，当某一个服务下线或者新增一个节点的时候，在没有其他组件的时候要想实现动态增加或者减少节点必须需要手动的重启对应的微服务。所以就应运而生了注册中心，常见的注册中心有`nacos`、`Eurake`等，本篇主要介绍`nacos`，`Nacos`支持基于`DNS`和基于`RPC`的服务发现（可以作为`springcloud`的注册中心）、动态配置服务（可以做配置中心）、**动态DNS服务**。如下图：

![image-20210502221252641](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210502221252641.png)

## 2、nacos集群搭建

### 准备

- linux需要安装jdk
- linux安装maven
- [Linux安装mysql](https://wangjiabin.blog.csdn.net/article/details/91891985?utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.control&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.control)
- [下载nacos](https://github.com/alibaba/nacos/releases/)

### 配置数据库

在`nacos`中`config` `application.properties`配置文件中设置数据库，将`config`目录下的`nacos-mysql.sql`sql文件执行

```properties
spring.datasource.platform=mysql

### Count of DB:
db.num=1

### Connect URL of DB:
db.url.0=jdbc:mysql://192.168.56.101:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
db.user=root
db.password=fuzy123
```

### 集群配置文件

将`config`目录下的`cluster.conf.example`复制一份，重命名为`cluster.conf`文件，并新增集群节点，如下：

```tex
192.168.163.131:8848
192.168.163.132:8848
192.168.163.133:8848
```

### 启动

```shell
./bin/startup.sh
```

启动成功日志：

```shell
#-Xms2g -Xmx2g 默认运行时 JVM 要求 2G 可用内存
/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.275.b01-0.el7_9.x86_64/bin/java  -server -Xms2g -Xmx2g ...
...
#列出 Nacos 所有集群节点
INFO The server IP list of Nacos is [192.168.163.131:8848, 192.168.163.132:8848, 192.168.163.133:8848]
...
#Nacos 正在启动
INFO Nacos is starting...
...
#集群模式启动成功，采用外置存储 MySQL 数据库
INFO Nacos started successfully in cluster mode. use external storage
```

## 3、`nacos`集群（实现leader选举）

`nacos`类似于`mysql`的主从复制（当进行更新相关的操作时，由`mysql`的主节点进行处理更新功能，然后在通过`binlog`日志将数据同步到从节点）。另外在`nacos`集群中，如果`Leader`节点挂了，如何重新选举出主节点呢？在nacos中通过`raft`算法来实现`leader`选举的，下面详细的介绍下`raft`算法。

### raft算法

#### 相关概念

- `Leader`：接受client的更新请求，本地处理后再同步到其他的集群副本。
- `Follower`：接受来自于`Leader`的请求，同时充当选民的角色。数据与`Leader`节点一致。
- `Candidate`：当集群中`Leader`挂了或者第一次启动时，`Leader`需要从该角色中选举出来。
- `term`:任期，每发起一轮投票，任期都会自增，每个`Candidate`会维护一个本地`term`。
- `时间轮`：每个节点在称为`Candidate`之前，都携带一个随机时间倒计时，该时间是在150ms-300ms之间，当时间结束时，该节点就会自动变成`Follower`节点。有以下几种情况时间会重置：
  - 收到选举的请求
  - 收到`Leader`的`HeartBeat`（心跳），说明`Leader`存在，不需要重新选举
- 每轮选举`Follower`节点在收到选举请求时，都会重置自己的倒计时，并且在一次任期内只能发起一次投票。
- `Candidate`节点在拉选票的时候，会先投自己一票。

### `raft`主要活动

> - 选举`Leader`节点
> - 同步`Leader`节点数据

#### `Leader`节点的选举

> 备注：F代表`Follower`节点，C代表`Candidate`节点，`Leader`代表节点

##### 正常情况下选`Leader`

![image-20210503204806806](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210503204806806.png)

​																				**节点初始状态**

![image-20210503204821099](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210503204821099.png)

​																					**C2节点倒计时先结束**

![image-20210503204831059](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210503204831059.png)

​																				   **C2成为`Leader`节点**

以上三张图就是选举出`Leader`的流程，大概可以归纳成以下流程：

- 有五个节点集群，每个节点都有一个随机的倒计时，倒计时先结束的称为`Candidate`节点（图2）
- `Candidate`节点向其他节点发出消息，让其他节点选自己
- 其它几个节点都返回成功，这个节点就成为了`Leader`节点
- 同时`term`任期加1

如果有一个`Follower`节点因为网络故障挂掉，没有返回成功信息给`Candidate`节点怎么办？不用担心，在`raft`算法中，只要有一半的节点投支持票了，`Candidate`节点就会被选举成`Leader`节点（包括自己本身）。

##### `Leader`节点故障重新选举

![image-20210503212812711](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210503212812711.png)

​																					**Leader节点正常**

![image-20210503212925048](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210503212925048.png)

​																				**Leader节点挂了**

![image-20210503213253785](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210503213253785.png)

​																			 **重新选举出Leader节点**

![image-20210503213356245](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210503213356245.png)

​																						**Leader上线**

![image-20210503213505205](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210503213505205.png)

​																**之前的Leader节点降为Follower节点**

以上流程分析：

- 当`Leader`节点挂掉了，会重新发起选举。选举出`Leader`节点后，`term`任期加1；
- 当旧的`Leader`上线后，发现任期小于新的`term`任期，则自动降为`Follower`节点；

##### 多个`Candidate`情况下选举

> 场景：有五个节点，其中挂了一个节点；同时又有两个节点（假设A节点和B节点）称为`Candidate`节点。C、D节点成为`Follower`节点。因为之前选举过一次，所以假设此时的任期`term`为1。

- A、B节点会向C、D节点拉选票，假设A向C要选票，B向D要选票，此时A和B节点都有2个选票（加上自己的投票），同时A、B节点`term`加1，都为2。
- 因为不满足超过半数选票的规则（一共五个节点，超过半数是3），所以不满足。
- 此时倒计时优先结束的会重新发起选票，优先获得半数票数的节点会成为`Leader`节点。

#### 数据同步

类似与`Mysql`的主从同步；流程如下：

- `Leader`节点接受数据，将数据改成`uncommitted`状态
- `Leader`节点给其余的`Follower`节点发送请求数据，`Follower`节点将数据存入本地，状态也更改成`uncommitted`状态；
- `Follower`节点将数据暂时保存到本地后，同时返回给`Leader`节点`OK`状态；
- `Leader`节点如果收到超过一半成功的回复，就将状态改成`Committed`；
- `Leader`节点再次发送给`Follower`节点请求，`Follower`节点将日志状态改成`committed`

[raft算法动图演示](http://thesecretlivesofdata.com/raft/)

参考文章：

https://www.jianshu.com/p/8e4bbe7e276c

https://www.cnblogs.com/smileIce/p/11221765.html

## 4、注册服务流程

​	`nacos`服务端提供对应的接口`InstanceController`，客户端发起请求后，将对应的服务注册到`nacos`服务器上。源码可参考`com.alibaba.cloud.nacos.registry.NacosServiceRegistry#register`该方法，注册接口具体参数可参考[nacos-api文档](https://nacos.io/zh-cn/docs/open-api.html)。





