# Docker

## 1、什么是`docker`

​	`Docker`是一个用于开发、传送和运行应用程序的开放平台。`Docker`使您能够将应用程序与基础设施分开，以便您可以快速交付软件。使用`Docker`，您可以像管理应用程序一样管理基础设施。通过利用`Docker`的快速交付、测试和部署代码的方法，您可以显着减少编写代码和在生产中运行代码之间的延迟。以上的话术优点难以理解，我们不妨以部署`web`项目为例，来说明`docker`的作用，如下：

- 远古时代

  如下图，很早以前部署一个`web`项目，三层结构：基础结构支撑操作系统，操作系统上运行`Application`；但是该模式缺点很明显：成本高、部署慢、浪费资源、硬件限制、不利于迁移扩展。

  ![image-20210731222050699](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210731222050699.png)



- 虚拟化时代

  衍生出虚拟机概念，一个`windows`操作系统上可以运行多台虚拟机，每个虚拟机又能运行独立的`Application`，如下图所示；该结构缺点也很明显：虚拟机太重了，一上来占用较多物理资源，移植性差，资源利用率低等。

  ![image-20210731223002804](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210731223002804.png)

- 容器时代

  如下图所示，一个`linux`或者`windows`上可以运行多个`APP`，充分利用了资源。

  ![image-20210731223329246](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210731223329246.png)

## 2、`docker`的架构

![image-20210731223542797](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210731223542797.png)

​																													**docker架构图**

### The Docker Daemon

​	`Docker`守护进程 (`dockerd`) 侦听`Docker API`请求并管理`Docker`对象，例如图像、容器、网络和卷。守护进程还可以与其他守护进程通信以管理`Docker`服务。

### The Docker client

​	`Docker`客户端 (`docker`) 是许多`Docker`用户与`Docker`交互的主要方式。当您使用诸如`docker run`之类的命令时，客户端会将这些命令发送到`dockerd`，后者会执行这些命令。`docker`命令使用`Docker API`。`Docker`客户端可以与多个守护进程通信。

### Docker registries

​	`Docker`注册表存储`Docker`镜像。`Docker Hub`是一个任何人都可以使用的公共注册中心，`Docker`默认配置为在`Docker Hub`上查找镜像。您甚至可以运行自己的私有注册表，类似于maven仓库的功能。

​	当您使用`docker pull`或`docker run`命令时，所需的图像将从您配置的注册表中提取。当您使用`docker push`命令时，您的图像将被推送到您配置的注册表。

### Docker objects

​	当您使用`Docker`时，您是在创建和使用镜像、容器、网络、卷、插件和其他对象。以下是针对一些docker对象的简介。

#### `Images`（镜像）

​	镜像是一个只读模板，包含创建`Docker`容器的说明。您可以创建自己的映像，也可以仅使用其他人创建并在注册表中发布的映像。要构建您自己的镜像，您需要使用简单的语法创建一个`Dockerfile`，用于定义创建镜像和运行镜像所需的步骤。`Dockerfile`中的每条指令都会在镜像中创建一个层。当您更改`Dockerfile`并重建映像时，只会重建那些已更改的层。与其他虚拟化技术相比，这是使映像如此轻巧、小巧和快速的部分原因。

#### Containers（容器）

​	容器是图像的可运行实例，它与`Images`的关系就好比`java`中类与对象的关系。您可以使用`Docker API`或`CLI`创建、启动、停止、移动或删除容器。您可以将容器连接到一个或多个网络，为其附加存储，甚至可以根据其当前状态创建新映像。

​	**默认情况下，容器与其他容器及其主机相对隔离。您可以控制容器的网络、存储或其他底层子系统与其他容器或主机之间的隔离程度。**

​	容器由其映像以及您在创建或启动它时提供给它的任何配置选项定义。**当容器被移除时，未存储在持久存储中的对其状态的任何更改都会消失。**

**Example**

以上概念可能刚接触会比较难懂，下面我们以`docker run`示例`docker`运行命令，来更好的理解上文中的相关概念，命令如下：

```shell
#该命令运行 ubuntu 容器，以交互方式附加到本地命令行会话，并运行 /bin/bash
docker run -i -t ubuntu /bin/bash
```

- 如果您在本地没有`ubuntu`映像，`Docker`会从您配置的注册表中提取它，就像您手动运行`docker pull ubuntu`一样。这就像`maven`拉去`jar`包一样，本地如果已经拉取，则不需要去远程仓库获取；反之去远程仓库下载。
- `Docker`创建一个新容器，就像您手动运行`docker container create`命令一样。
- `Docker`为容器分配一个读写文件系统，作为它的最后一层。这允许正在运行的容器在其本地文件系统中创建或修改文件和目录。可以使用`/bin/bash`命令进入容器中，修改对应的文件。
- `Docker`创建一个网络接口来将容器连接到默认网络，因为您没有指定任何网络选项。这包括为容器分配`IP`地址。默认情况下，容器可以使用主机的网络连接连接到外部网络。也就是说如果你的操作系统是`linux`，您在该系统上创建的容器，你通过`linux`是可以访问到容器里面相关的应用的。
- `Docker`启动容器并执行`/bin/bash`。由于容器以交互方式运行并连接到您的终端（由于`-i`和`-t`标志），您可以在输出记录到终端时使用键盘提供输入。
- 当您键入`exit`来终止`/bin/bash`命令时，容器会停止但不会被移除。您可以重新启动或删除它。

## 3、快速开始

在`centos`安装`docker`

### 卸载旧版本

```shell
sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
```

### 安装`docker`引擎

```shell
#该命令是安装最新版本的docker
sudo yum install docker-ce docker-ce-cli containerd.io

#以下命令是安装指定版本docker
#查询docker版本号
yum list docker-ce --showduplicates | sort -r
#安装指定版本
sudo yum install docker-ce-<VERSION_STRING> docker-ce-cli-<VERSION_STRING> containerd.io
```

### 启动`docker`服务

```shell
sudo systemctl start docker
```

### 验证`docker`

```shell
sudo docker run hello-world
```

### 卸载`docker`

- 第一步，下载`docker`引擎客户端和容器相关的包

  ```shell
  sudo yum remove docker-ce docker-ce-cli containerd.io
  ```

- 第二步，移除镜像、`volumes`等

  ```shell
   sudo rm -rf /var/lib/docker
   sudo rm -rf /var/lib/containerd
  ```

### `docker`安装`mysql`示例

- 拉取`mysql`镜像

  ```shell
  docker pull mysql:latest
  ```

- 根据镜像创建容器

  ```shell
  #186f9359e77a84a9c48fa4f09968a09144967b38b0f1e8f59b5aeb85692a7276 容器id，--name容器名称
  [root@localhost ~]# docker run -itd --name mysql-test -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql
  186f9359e77a84a9c48fa4f09968a09144967b38b0f1e8f59b5aeb85692a7276
  ```

- 进入到`container`容器中

  ```shell
  [root@localhost ~]# docker exec -it mysql-test bash
  #进入容器中
  root@186f9359e77a:/# mysql -h localhost -u root -p 123456
  ```

- 其他命令

  - `docker ps`：查看运行中的`container`
  - `docker ps -a`：查看所有的`container`[包含退出的]
  - `docker rm containerid`：删除`container`
  - `docker rm -f $(docker ps -a) `：删除所有`container`
  - `docker stop/start container`：停止/启动容器

## 4、`Dockerfile`文件

`Docker`通过从`Dockerfile`中读取指令来自动构建镜像——`Dockerfile`是一个包含构建给定镜像所需的所有命令的文本文件。`Dockerfile`遵循特定的格式和指令集。`Docker`镜像由只读层组成，每个层代表一个`Dockerfile`指令。这些层是堆叠的，每一层都是前一层变化的增量。参考下面的`Dockerfile`:

```dockerfile
# syntax=docker/dockerfile:1
FROM ubuntu:18.04
COPY . /app
RUN make /app
CMD python /app/app.py
```

每条指令创建一层：

- `FROM`从`ubuntu:18.04 Docker`镜像创建一个层。
- `COPY`从`Docker`客户端的当前目录添加文件。
- `RUN`使用`make`构建您的应用程序。
- `CMD`指定要在容器内运行的命令。

当你运行一个镜像并生成一个容器时，你会在底层的顶部添加一个新的可写层（“容器层”）。对正在运行的容器所做的所有更改，例如写入新文件、修改现有文件和删除文件，都将写入此可写容器层。

### `Dockerfile`结构

#### FROM

```dockerfile
#ROM 指令指定您正在构建的父镜像
FROM ubuntu:14.04
```

#### RUN

```dockerfile
#RUN 指令将在当前镜像之上的新层中执行任何命令并提交结果。生成的提交镜像将用于 Dockerfile 中的下一步。
RUN groupadd -r mysql && useradd -r -g mysql mysql
```

#### CMD

```dockerfile
#容器启动的时候默认会执行的命令，一个docker只能有一个CMD命令；若有多个CMD命令，则最后一个生效；如果用户为 docker run 指定参数，则它们将覆盖 CMD 中指定的默认值。
CMD mysqld
```

#### ENV

```dockerfile
#设置变量的值，后面可以直接使用${MYSQL_MAJOR}来进行引用
EVN MYSQL_MAJOR 5.7
```

#### LABEL

```dockerfile
#设置镜像标签；一个镜像可以有多个标签
LABEL version="1.0"

#查看标签命令
docker image inspect --format='' myimage
```

#### VOLUME

```dockerfile
#指定数据的挂载目录
VOLUME /var/lib/mysql
```

#### COPY

```dockerfile
#COPY 指令从 <src> 复制新文件或目录，并将它们添加到路径 <dest> 处的容器文件系统中。注意只是复制，不会提取和解压
COPY docker-entrypoint.sh /usr/local/bin/
```

#### ADD

```dockerfile
#ADD 指令从源 复制新文件、目录或远程文件 URL，并将它们添加到路径 目标 处的图像文件系统。ADD会对压缩文件提取和解压
ADD application.yml /etc/fuzy/
```

#### ENTRYPOINT

```dockerfile
#ENTRYPOINT 允许您配置将作为可执行文件运行的容器。
ENTRYPOINT ["docker-entrypoint.sh"]
```

#### EXPOSE

```dockerfile
#指定容器在运行时要暴露的端口，启动镜像时，可以使用-p将该端口映射给宿主机
EXPOSE 3306
```

#### `Dockerfile`基于Spring Boot项目实战

```java
(1)创建一个Spring Boot项目
(2)写一个controller
@RestController
public class DockerController {
    
    @GetMapping("/dockerfile")
    @ResponseBody
    String dockerfile() {
    	return "hello docker" ;
    }
}

(3)mvn clean package打成一个jar包
在target下找到"dockerfile-demo-0.0.1-SNAPSHOT.jar"
(4)在docker环境中新建一个目录"first-dockerfile"
(5)上传"dockerfile-demo-0.0.1-SNAPSHOT.jar"到该目录下，并且在此目录创建Dockerfile
(6)创建Dockerfile文件，编写内容
FROM openjdk:8
MAINTAINER itcrazy2016
LABEL name="dockerfile-demo" version="1.0" author="itcrazy2016"
COPY dockerfile-demo-0.0.1-SNAPSHOT.jar dockerfile-image.jar
CMD ["java","-jar","dockerfile-image.jar"]
(7)基于Dockerfile构建镜像
docker build -t test-docker-image .
(8)基于image创建container
docker run -d --name user01 -p 6666:8080 test-docker-image
(9)查看启动日志docker logs user01
(10)宿主机上访问curl localhost:6666/dockerfile
hello docker
(11)还可以再次启动一个
docker run -d --name user02 -p 8081:8080 test-docker-image
```

> **注意点**：
>
> 1. 文件没有后缀，名字就是`Dockerfile`
> 2. 命令约定全部使用大写，如`RUN,ADD,FROM`
> 3. 第一条命令必需是FROM，作用是指定在哪个基础镜像上创建镜像。
> 4. 注释以`“#”`标记

[docker语法](https://docs.docker.com/engine/reference/builder/#from)











