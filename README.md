![](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/banner.png)
---

 [![Release Version](https://img.shields.io/badge/release-2.0.0-brightgreen.svg)](https://gitee.com/iotechn/unimall) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://gitee.com/iotechn/unimall/pulls)


[(English Documents Available)](readme_en.md)

> **社区版**

Unimall 针对中小商户、企业和个人学习者开发。使用Java编码，采用SpringBoot、Mybatis-Plus等易用框架，适合个人学习研究。同时支持单机部署、集群部署，中小商户企业可根据业务动态扩容。unimall使用uniapp前端框架，可同时编译到 微信小程序、H5、Android App、iOS App等几个平台，可为中小商户企业节约大量维护成本。也可支撑中小商户企业前期平台横扩需求。

Unimall社区版源码包含:

- Java 后端服务
    - unimall-launcher: 启动器（打包打这个就行）
    - unimall-admin-api: 提供管理员管理系统的WebApi
    - unimall-app-api: 提供APP、小程序、H5用户请求的WebApi
    - unimall-biz: 提供通用业务代码
    - unimall-data: 提供数据模型以及数据访问层封装
    - unimall-core: 提供注解、工具类等
    
- Vue 前端页面
    - unimall-admin: 基于element-ui的后台管理页面
    - unimall-app: 基于uniapp的小程序、H5、APP前端代码

- sql: 数据库初始化SQL脚本

## About this repository

- release: 已经发布的分支
- develop: 正在开发的分支
   - develope-v2: 此分支已经完全测试过，但还没上过生产，可直接使用

## Contact

QQ讨论群：656676341(1群已满) 940197916(2群) (进群前，请在网页右上角点star)

微信：18584669549 (若微信群二维码超100人，请加此微信备注意图，接受邀请)

微信群(初建)：

![front](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/wx.png)

## Experience

- Client
  
  - 扫描下面二维码体验不同终端
  

![qr](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/qr.png)

- Pages

![front](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/front.jpg)

- Admin
  - 后台演示地址: [http://unimallv2.iotechn.com/](http://unimallv2.iotechn.com/)
  - 登录名:guest 密码:123456 验证码:666666 (guest仅有只读权限，无读配置权限)
- Pages

![front](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/b1.png)
![front](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/b2.png)
![front](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/b3.png)
![front](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/b4.png)
![front](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/b5.png)


## New Features (v2)
| 描述 | 演示 |
|:--------|:-------:|
| - 完全重新设计商品缓存，可实现无数据库访问商品访问，大幅度提高QPS | 无 |
| - 支持注解式简单切面缓存，大幅度降低冗余代码 | ![cahce](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/cache.png) |
| - 支持搜索引擎Aliyun OpenSearch，可支持高频文字检索 | 无 |
| - 修复v1中由于缓存更新不及时，无法及时显示销量问题 | 无 |
| - 支持动态配置，配置文件可做到可视化配置；之后Unimall项目中只需要静态配置MySql 与 Redis 即可 | ![config](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/config.png) |
| - 支持多维度SKU，例如衣服可增加规格维度: 颜色、尺寸| ![sku_attr](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/sku_attr.png) |
| - 升级持久层框架由mybatis-plus 2 至 mybatis-plus 3，引入分页插件 | 无 |
| - 修改类目管理方式，改为直观三级树形式 | 无 |
| - 增加管理员操作日志 | 无 |



## The Framework

![framework](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/framework.png)



## Getting started


服务器可根据自身业务来选购，单机环境推荐2C4G

##### ① 基础运行环境

| 运行环境 | 版本号 |
|:--------|:--------|
|  MySQL   |  5.7+（推荐）  |
|  JDK   |  1.8+（推荐）  |
|  Redis   |  4+   |
|  Nginx  |  只要Web容器就可以了  |

请参考 [CentOS7.4 安装 MySQL5.7](https://github.com/iotechn/document-basic/blob/master/CentOS7.4_Install_MySQL5.7.md)

请参考 [CentOS 安装 JDK8](https://github.com/iotechn/document-basic/blob/master/CentOS_Install_JDK8.md)

请参照CentOS [安装 NodeJs 8.15.0](https://github.com/iotechn/document-basic/blob/master/CentOS_Install_NodeJS_8.15.0.md)

Redis安装可直接使用yum安装 

	yum install redis

安装完成后使用 redis-cli 命令，若能进入，则表示redis安装完成

##### ② 后台管理系统编译运行指南

[编译运行指南](doc/run.md)

##### ③ 编译部署前后端代码

项目部署分为 Server 、 App 、 Admin 三个部分

[部署Step1:Java后台编译&部署](doc/server.md)

[部署Step2:App编译打包](doc/app.md)

[部署Step3:Admin编译打包](doc/admin.md)

##### ④ 二次开发文档

[二次开发文档](doc/2develop.md)

## Copyright

本项目后端由重庆驽驹科技有限公司开发，禁止未经授权用于商业用途。个人学习可免费使用。如需商业授权，请进DEMO小程序购买。

1.淘宝授权：https://item.taobao.com/item.htm?spm=a2126o.11854294.0.0.5f164831jltJlx&id=615046003598 2.直接在小程序中购买商用授权商品

已授权公司查询(由于是后面统计的，有些未统计到，请用订单号来添加)
http://www.dobbinsoft.com/authorization.html

![证书](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/cert.jpg)

前端代码使用的 mix.R 的开源模板，在此模板的基础上对接了Unimall后端Api，作者说不做商用限制，保留原地址，所以授权仅针对后端代码商用授权。

mix模板地址：https://ext.dcloud.net.cn/plugin?id=200

## Service

### 项目定制开发服务

若需要软件需求外包，小程序、App、网站、微信定制开发，请联系微信：18584669549 （请备注意图）

### SaaS 服务 (无代码快速上线微信小程序)

对于缺少技术的创业团队来说，使用SaaS服务即可解决掉技术问题。简单的说，就是由我们为客户统一运维，每年付一定的钱给我们就行了。

Unimall SaaS版每年980元。这个价格就和服务器的价格差不多，并且<u>**我们还可以免费代申请微信小程序，这可以节约每年300元的认证费**</u>。也就是说你使用SaaS服务之后，还可以省下三四百元每年。

使用Unimall SaaS版的优势：Unimall本身开源，在创业初级技术不足，不足以支撑自主研发。当使用Unimall SaaS累计到一定用户后，可无缝切换到Unimall开源商城，并在此基础上做二次开发。

支持试用：如果您已经有了自己的小程序，直接来试用下吧。微信扫描一下二维码直接购买，可获取免登录链接，或直接PC访问腾讯云市场链接。

![saas](https://unimall-asset.oss-cn-beijing.aliyuncs.com/readme/saas.png)

腾讯云市场链接：[https://market.cloud.tencent.com/products/18338](https://market.cloud.tencent.com/products/18338)

点击“购买”旁边的“试用7天”，可立即试用。

## Contributing

如果你有好的意见或建议，欢迎给我们提 Issues 或 Pull Requests，为Unimall开源商城贡献力量。关于分支/issue及PR。
