##后台管理系统编译运行指南

###Java后台代码编译运行
详细情况查看[Java后台编译&部署](server.md)前两步,将配置文件配置好

配置文件位于 /unimall-launcher/src/main/resources/application-prd.properties 将这些信息配置好，就可以了。

注意事项:

1. 配置文件配置好后,运行unimall-launcher项目即可,这是unimall项目后台代码的启动器.
2. 电脑中必须装有redis,maven.


###Java前端代码编译运行

后台管理系统的前端代码是使用vue框架写的,命令行进入unimall-admin项目目录下运行下面两条命令
```
1. cnpm install (是IDEA在这条命令前请先看注意事项)
2. cnpm run dev
```

注意事项

1. 电脑必须安装node.js环境,CentOS安装步骤[NodeJs 8.15.0](https://github.com/iotechn/document-basic/blob/master/CentOS_Install_NodeJS_8.15.0.md)
2. 在install前,请按步骤[IDEA忽略文件设置](https://www.cnblogs.com/sxdcgaq8080/p/9007883.html)忽略node_modules文件.
3. 跳过验证码技巧: 先去数据库录入一个admin用户 --> 进入redis-cli : set admin_msg_code_(admin的电话) (验证码)  --> 这样你就可以输入自己设置的验证码进入.

