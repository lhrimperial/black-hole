### Kubernetes

> Kubernets 着重解决的问题：处理运行在大规模集群中的各种任务之间的各种各样的关系



##### 组成 

1. master节点
   - kube-apiserver：负责API服务
   - kube-scheduler：负责调度
   - kube-controller-manager：负责容器编排
2. node节点
   - kubelet：负责同容器运行时打交道
     - CRI（Container Runtime Interface）：容器运行时各项核心操作的远程调用接口
     - Device Plugin：管理GPU等宿主物理设备
     - CNI（Container Networking Interface）网络插件配置网络
     - CSI（Container Storage Interface）存储插件持久化存储



首先，关于 Pod 最重要的一个事实是：它只是一个逻辑概念。Pod，其实是一组共享了某些资源的容器。Pod 里的所有容器，共享的是同一个 Network Namespace，并且可以声明共享同一个 Volume。

> Pod 才是Kubernetes项目中最小的编排单位，而不是容器。
>
> Pod 扮演的是传统部署环境里“虚拟机”的角色。
>
> 凡是调度、网络、存储，以及安全相关的属性，基本上是 Pod 级别的。



