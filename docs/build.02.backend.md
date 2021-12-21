> 本文讲解 编译/打包/发布 后端服务。



## 依赖

v3之后，将基础框架与业务相分离，这样做的主要目的是为了能够快速搭建统一脚手架。因为公司也有许多其他的外包项目。所以编译之前，需要编译依赖的其他几个项目。

| 依赖（点击打开）                 | 解释                   |
| -------------------------------- | ---------------------- |
| [core](../dobbinfw-core)         | 核心包                 |
| [support](../dobbinfw-support)   | 支持包，非常重要的文档 |
| [launcher](../dobbinfw-launcher) | 启动器，非常重要的文档 |
| [pay](../matrix-pay) | 支付包 |

以上详细文档，请参照模块项目README。

安装依赖，按顺序依赖包，每一个包的安装方式，在项目README中都有。



## 配置数据源

Unimall静态配置，主要是配置两个数据源，即redis和mysql。

找到配置文件，分别是开发配置和生产配置：

unimall-runner/src/main/resources/application.properties

unimall-runner/src/main/resources/application-prd.properties

将以下配置修改为自己的配置

```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.redis.host=
spring.redis.password=
spring.user-redis.host=
spring.user-redis.password=
spring.lock-redis.host=
spring.lock-redis.password=
```



## 打包Unimall

检出Unimall代码，并通过命令行进入代码根目录。执行以下打包命令。

```bash
mvn clean package -Dmaven.test.skip=true
```

打包完成后，会生成文件：

unimall-runner/target/unimall-runner-v3.jar



-- Docker



## 发布Unimall

使用XShell连接到远程服务器。并执行rz命令：

```
rz
```

在弹出的文件选择框中，选择unimall-runner/target/unimall-runner-v3.jar

传输完成后，使用java命令运行jar包

```
nohup java -jar unimall-runner-v3.jar --spring.profiles.active=prd > /dev/null &
```

查看日志

```
tail -f logs/unimall/unimall.log
```

看到 Started UnimallRunnerApplication in 7.358 seconds (JVM running for 8.398) 类似信息，则表示服务启动成功！