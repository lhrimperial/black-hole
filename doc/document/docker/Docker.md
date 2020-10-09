####Docker

##### Docker 安装

1. [官方地址](https://docs.docker.com/install/linux/docker-ce/centos/)

   - 启动

     $ sudo systemctl enable docker

     $ sudo systemctl start docker

   - 建立 `docker` 组：

     $ sudo groupadd docker

     `$ sudo usermod -aG docker $USER` 加入当前用户

   - docker container ls 查看容器信息

   - docker run -itd ubuntu:18.04 /bin/bash 容器后台运行，新建容器时候不进入容器

     - -t 选项让Docker分配一个伪终端（pseudo-tty）并绑定到容器的标准输入上，
     - -i 则让容器的标准输入保持打开
     - -d 容器后台运行，不进入容器(不直接调出终端，要进入容器使用docker exec -it 243c32535da7 /bin/bash)

   - docker ps 查看当前运行的容器所有信息

   - docker ps -a 查看已经终止的容器所有信息

   - docker start container_id 命令，直接将一个已经终止的容器启动运行

   - docker stop container_id 终止容器

   - 删除容器

     - docker rm 容器名称或ID
     - docker rm -f 容器名称或ID
     - docker ps -a 查看已终止的容器
     - docker ps -l 查看最后一次创建的容器

   - 运行容器 docker run 

     - -d:让容器在后台运行。
     - -P : 是容器内部端口随机映射到主机的高端口。
     - -p : 是容器内部端口绑定到指定的主机端口。

   - docker-compose

     - docker-compose up 启动多容器应用，进入
     - docker-compose up -d 启动多容器应用，后台运行
     - ctrl + d 退出应用终端，回到用户终端
     - docker-compose ps 查看后台容器信息或者已停止容器信息
     - docker-compose ps -a 查看已停止容器信息
     - docker-compose stop 停止容器
     - docker-compose start 重启已停止的容器
     - docker-compose rm 删除已停止的容器
     - docker-compose down 停止并删除所有的容器

   - docker system df 查看镜像、容器、数据卷所占用的空间

   

2. 常用命令
   - 删除所有镜像
     docker rmi `$(docker images -q)`
   - 停用全部运行中的容器:
     docker stop $(docker ps -q)
   - 删除全部容器：
     docker rm $(docker ps -aq)
   - 一条命令实现停用并删除容器：
     `docker stop $(docker ps -q) & docker rm $(docker ps -aq)`
   - docker exec -it containerName bash

3. https://zhuanlan.zhihu.com/p/89587030



######docker 开启remote API

docker默认是没有开启Remote API的，需要我们手动开启。

编辑`/lib/systemd/system/docker.service`文件：

```
ExecStart=/usr/bin/dockerd -H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375
#ExecStart=/usr/bin/dockerd -H fd://

重启服务
sudo systemctl daemon-reload

sudo service docker restart


```



#### 文章

https://blog.csdn.net/danny_idea/category_9494940.html



https://blog.csdn.net/weixin_43314519/article/details/107133526


### ZK
docker exec -it first-zk zkCli.sh