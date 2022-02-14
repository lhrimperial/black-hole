### Shell

#### 概念

> ​	Shell 是一个用 C 语言编写的程序，它是用户使用 Linux 的桥梁。Shell 既是一种命令语言，又是一种程序设计语言。
>
> ​	Shell 是指一种应用程序，这个应用程序提供了一个界面，用户通过这个界面访问操作系统内核的服务。

1. 脚本解释器

   - sh：即Bourne shell，POSIX（Portable Operating System Interface）标准的shell解释器，它的二进制文件路径通常是/bin/sh，由Bell Labs开发。
   - Bash是Bourne shell的替代品，属GNU Project，二进制文件路径通常是/bin/bash。

2. 运行

   - 作为可执行文件运行

     ```
     chmod +x test.sh
     ./test.sh
     ```

     

   - 作为解释器参数运行

     ```
     /bin/sh test.sh
     ```

     