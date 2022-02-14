### Docker

#### docker 安装启动失败

1. 禁用selinux

   ```
   vi /etc/selinux/config 
   
   # SELINUX=disabled
   # 重启
   
   禁用防火墙
   systemctl status firewalld.service           #查看firewall状态
   systemctl stop firewalld.service             #关闭firewall
   systemctl list-unit-files |grep firewalld    #查看firewalld是否开机自动启动，若显示enabled为开机自启，显示disable为开机不自启
   systemctl disable firewalld.service       #禁止开机自启
   ```

2. 防火墙问题

   ```
   firewall-cmd --zone=trusted --remove-interface=docker0
   ```

3. 配置docker镜像源加速器

   ```
   vi /etc/docker/daemon.json
   
   {
     "registry-mirrors": [
       "https://hub-mirror.c.163.com",
       "https://mirror.baidubce.com"
     ]
   }
   
   # 重启服务
   sudo systemctl daemon-reload
   sudo systemctl restart docker
   ```

4. 更新centos 源

   ```
   yum clean all
   
   yum makecache
   
   yum -y update
   ```

   

#### docker 常用命令

1. docker image ls （docker images）列出已下载的镜像

   - `-f` 过滤
   - `-q`只列出镜像ID

2. docker system df  查看镜像、容器、数据卷所占用空间

3. docker image rm 删除镜像

   ```
   $ docker image rm [选项] <镜像1> [<镜像2> ...]
   
   $ docker image rm $(docker image ls -q redis) //删除某一类镜像
   ```

4. docker commit 将容器存储层保存下来成为镜像

   ```
   docker commit [选项] <容器ID或容器名> [<仓库名>[:<标签>]]
   ```

5. `docker build` 命令进行镜像构建

   ```
   docker build [选项] <上下文路径/URL/-> # 构建镜像上下文路径，docker build 命令得知这个路径后，会将路径下的所有内容打包，然后上传给 Docker 引擎
   eg: docker build -t myip .
   ```
   
   

#### Dockerfile 指令

1. FROM 指定基础镜像

2. RUN 用来执行命令行的

   - *shell* 格式：`RUN <命令>`，就像直接在命令行中输入的命令一样
   - *exec* 格式：`RUN ["可执行文件", "参数1", "参数2"]`，这更像是函数调用中的格式

3. COPY  指令将从构建上下文目录中 `<源路径>` 的文件/目录复制到新的一层的镜像内的 `<目标路径>` 位置

   ```
   COPY [--chown=<user>:<group>] <源路径>... <目标路径>
   COPY [--chown=<user>:<group>] ["<源路径1>",... "<目标路径>"]
   ```

4. `ADD` 指令和 `COPY` 的格式和性质基本一致

   COPY 和 ADD 指令中选择的时候，可以遵循这样的原则，所有的文件复制均使用 COPY 指令，仅在需要自动解压缩的场合使用 ADD

5. `CMD` 指令的格式和 `RUN` 相似，也是两种格式：
   - `shell` 格式：`CMD <命令>`
   - `exec` 格式：`CMD ["可执行文件", "参数1", "参数2"...]`
   - 参数列表格式：`CMD ["参数1", "参数2"...]`。在指定了 `ENTRYPOINT` 指令后，用 `CMD` 指定具体的参数。
   
6. ENTRYPOINT 的格式和 RUN 指令格式一样，分为 exec 格式和 shell 格式。ENTRYPOINT 的目的和 CMD 一样，都是在指定容器启动程序及参数。当指定了 `ENTRYPOINT` 后，`CMD` 的含义就发生了改变，不再是直接的运行其命令，而是将 `CMD` 的内容作为参数传给 `ENTRYPOINT` 指令

   - 让镜像变成像命令一样使用
   - 应用运行前的准备工作

7. ENV 设置环境变量，使用方式`$key`，格式有两种：

   - `ENV <key> <value>`
   - `ENV <key1>=<value1> <key2>=<value2>...`

8. ARG 构建参数，构建参数和 `ENV` 的效果一样，都是设置环境变量。

   格式：`ARG <参数名>[=<默认值>]`

9. VOLUME 定义匿名卷

   格式为：

   - `VOLUME ["<路径1>", "<路径2>"...]`
   - `VOLUME <路径>`

10. EXPOSE 暴露端口，`EXPOSE` 指令是声明容器运行时提供服务的端口

    格式为 `EXPOSE <端口1> [<端口2>...]`

    > 要将 `EXPOSE` 和在运行时使用 `-p <宿主端口>:<容器端口>` 区分开来。`-p`，是映射宿主端口和容器端口，换句话说，就是将容器的对应端口服务公开给外界访问，而 `EXPOSE` 仅仅是声明容器打算使用什么端口而已，并不会自动在宿主进行端口映射。

11. WORKDIR 指定工作目录

    格式为 `WORKDIR <工作目录路径>`

12. USER 指定当前用户，`USER` 指令和 `WORKDIR` 相似，都是改变环境状态并影响以后的层

    格式：`USER <用户名>[:<用户组>]`

13. HEALTHCHECK 健康检查

    - `HEALTHCHECK [选项] CMD <命令>`：设置检查容器健康状况的命令
    - `HEALTHCHECK NONE`：如果基础镜像有健康检查指令，使用这行可以屏蔽掉其健康检查指令



#### 附录

##### 基本原理

##### 注意事项

1. Union FS 是有最大层数限制的，曾经42层，现在不得超过127层
2. Linux 命令工具：https://www.linuxcool.com/echo
2. 





























