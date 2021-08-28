#### Linux 基础

##### 一 常用命令说明

```shell
nohup command >out.file 2>&1 &
```

“1”表示文件描述符 1，表示标准输出，“2”表示文件描述符 2，意思是标准错误输出，“2>&1”表示标准输出和错误输出合并了，合并到哪里去呢？到 out.file 里。

```shell
ps -ef |grep 关键字 |awk '{print $2}'|xargs kill -9
```

 ps -ef 列出所有正在运行的程序，grep 通过关键字找到程序，awk 工具可以很灵活地对文本进行处理，这里的 awk '{print $2}'是指第二列的内容，是运行的程序 ID。我们可以通过 xargs 传递给 kill -9。



##### 查看线上日志常用命令

https://www.jianshu.com/p/e4a6eef6f590

