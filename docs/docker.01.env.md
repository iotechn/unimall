> 使用容器化部署，可直接丢到k8s中运行。
>
> 注意： 容器化部署，只能自动部署 后端 和 后台前端



## 编译

#### 在容器机器上编译

在容器机器上执行初始化编译环境脚本：

init_builder.sh

在容器机器上做以下两步：

参照文档： [后端部署](./build.02.backend.md) 执行到Docker标签以上。

参照文档： [后端部署](./build.03.front.md) 执行到Docker标签以上。

编译好后，将域名ssl放入env目录。

在根目录执行命令

```
docker build -t unimall .
```

执行完后，会多出一个镜像 unimall

运行容器：

```
docker run -d -e MYSQL_HOST=10.0.20.2 -e REDIS_HOST=10.0.20.2:6379 -p 80:80 -p 443:443 unimall
```

数据库用户密码等更多-e配置，请参考 unimall-runner/src/main/resources/application-prd.properties

使用docker logs看下是否成功启动。



> 🍭注意： 这种方式一般不会这么玩，一般来说，编译服务器和运行服务器分离的。开发者可自己搭建Jenkins等方式搭建编译机。下面演示使用阿里云CICD构建并发布。



#### 通过阿里云CICD编译发布

参照视频：  TODO