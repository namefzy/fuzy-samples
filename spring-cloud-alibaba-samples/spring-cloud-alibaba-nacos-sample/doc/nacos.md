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

## 3、nacos集群（实现leader选举）







