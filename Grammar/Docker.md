##### 一、Docker简介

###### 1、概述

- 是一种系统平滑移植，容器虚拟化技术
- 通过镜像(images)将作业系统核心除外，运作应用程式所需要的系统环境，由下而上打包，达到应用程式跨平台间的无缝接轨运作
- Docker的主要目标是使用户的APP（可以是一个WEB应用或数据库应用等等）及其运行环境能够做到“一次镜像，处处运行”
- Linux容器技术的出现就解决了这样一个问题，而 Docker 就是在它的基础上发展过来的，将应用打成镜像，通过镜像成为运行在Docker容器上面的实例，而 Docker容器在任何操作系统上都是一致的，这就实现了跨平台、跨服务器
- 总结：解决了运行环境和配置问题的软件容器，方便做持续集成并有助于整体发布的容器虚拟化技术

###### 2、容器与虚拟机比较

- 传统虚拟机技术是虚拟出一套硬件后，在其上运行一个完整的操作系统，在该系统上再运行所需应用进程
- 容器内的应用进程直接运行于宿主的内核，容器内没有自己的内核且也没有进行硬件虚拟，因此容器要比传统虚拟机更为轻便
- 每个容器之间互相隔离，每个容器有自己的文件系统，容器之间进程不会相互影响，能区分计算资源



##### 二、Docker安装

###### 1、前提条件

- Docker不是一个通用的容器工具，它依赖于已存在并运行的Linux内核环境，Docker实质上是已经运行的Linux下制造了一个隔离的文件环境，因此执行效率几乎等同于所部署的Linux主机，所以Docker必须部署在Linux内核的系统上

###### 2、Docker的基本组成

- 镜像（Image）：是一个只读的模板，镜像可以用来创建Docker容器，一个镜像可以创建很多容器
- 容器（Container）：
    - Docker 利用容器（Container）独立运行的一个或一组应用，应用程序或服务运行在容器里面，容器就类似于一个虚拟化的运行环境，容器是用镜像创建的运行实例
    - 可以把容器看做是一个简易版的 Linux 环境（包括root用户权限、进程空间、用户空间和网络空间等）和运行在其中的应用程序
- 仓库（Repository）：集中存放镜像文件的场所
- 工作原理：Docker是一个Client-Server结构的系统，Docker守护进程运行在主机上， 然后通过Socket连接从客户端访问，守护进程从客户端接受命令并管理运行在主机上的容器

###### 3、安装步骤

- 安装gcc相关
    - `yum -y install gcc`
    - `yum -y install gcc-c++`
- 安装需要的软件包
    - `yum install -y yum-utils`
- 设置stable镜像仓库（自定义为阿里云的仓库）
    - `yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo`
- 更新yum软件包索引（非官方推荐，只是为了yum下载速度快一点）
    - `yum makecache fast`
- 安装Docker CE
    - `yum -y install docker-ce docker-ce-cli containerd.io`
- 启动Docker
    - `systemctl start docker`
- 测试是否安装成功
    - `docker version`
    - `docker run hello-world`

- 阿里云镜像加速器（非官方推荐）

    - ```shell
        sudo mkdir -p /etc/docker
        sudo tee /etc/docker/daemon.json <<-'EOF'
        {
          "registry-mirrors": ["https://eplysjiq.mirror.aliyuncs.com"]
        }
        EOF
        sudo systemctl daemon-reload
        sudo systemctl restart docker
        ```

###### 4、原理

- 开始（run命令）->Docker在本机中寻找该镜像
    - ->有该镜像->以该镜像为模板生产容器实例运行
    - ->没有镜像->去DockerHub上查找该镜像
        - ->Hub上可以找到->下载镜像到本地->以该镜像为模板生产容器实例运行
        - ->Hub上没有该镜像->返回失败错误，找不到该镜像



##### 三、Docker常用命令

###### 1、帮助启动类命令

- 启动docker： `systemctl start docker`
- 停止docker： `systemctl stop docker`
- 重启docker： `systemctl restart docker`
- 查看docker状态： `systemctl status docker`
- 开机启动： `systemctl enable docker`
- 查看docker概要信息： `docker info`
- 查看docker总体帮助文档： `docker --help`
- 查看docker命令帮助文档： `docker 具体命令 --help`

###### 2、镜像命令

- `docker images`
    - 列出本地主机上的镜像，其中TAG表示镜像的标签版本号，同一仓库源可以有多个TAG版本，如果不指定一个镜像的版本标签，将默认使用latest（最新版）
    - OPTIONS说明：
        - -a：列出本地所有的镜像（含历史映像层）
        - -q：只显示镜像ID（镜像主键在所有仓库中是唯一的）
- `docker search 镜像名字`
    - 去DockerHub查询该镜像的资源
    - OPTIONS说明：
        - --limit N：只列出N个镜像，默认25个
- `docker pull 镜像名字`
    - 将远程库的镜像下载到本地库
    - OPTIONS说明：
        - [:TAG]：表示下载该镜像TAG版本，如果不加，则默认下载latest最新版，如docker pull redis:6.0.8，则表示下载Redis的6.08版本
- `docker system df`
    - 查看镜像/容器/数据卷所占的空间
- `docker rmi 镜像名字`
    - 删除镜像
    - OPTIONS说明：
        - `docker rmi -f 镜像id`：删除单个（其中-f是强制删除的意思，也可以不加，可以用镜像名或镜像id来指定要删除的镜像）
        - `docker rmi -f 镜像名1:TAG 镜像名2:TAG`：批量删除指定的多个镜像
        - `docker rmi -f ${docker images -qa}`：删除所有镜像，也可以组合使用`docker images`删除筛选出来的镜像
- 补充：什么是虚悬镜像
    - 仓库名、TAG都是none的镜像，称为虚悬镜像（dangling image），会在DockerFile中介绍

###### 3、容器命令

- `docker run [OPTIONS] IMAGES [COMMAND] [AGR...]`

    - 新建并启动容器

    - OPTIONS说明：

        - --name="容器新名字"    为容器指定一个名称

        - -d: 后台运行容器并返回容器ID，也即启动守护式容器(后台运行)

        - -i：以交互模式运行容器，通常与 -t 同时使用

        - -t：为容器重新分配一个伪输入终端，通常与 -i 同时使用。也即启动交互式容器(前台有伪终端，等待交互)；

        - -P: 随机端口映射，大写P

        - -p: 指定端口映射，小写p

            ![docker1](./img_md/docker1.png)

- `docker ps [OPTIONS]`

    - 列出当前所有正在运行的容器
    - OPTIONS说明：
        - -a：列出当前所有正在运行的容器+历史上运行过的
        - -l：显示最近创建的容器
        - -n：显示最近n个创建的容器
        - -q：静默模式，只显示容器编号