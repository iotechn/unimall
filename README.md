> unimall 针对中小商户、企业和个人学习者开发。使用Java编码，采用SpringBoot、Mybatis-Plus等易用框架，适合个人学习研究。同时支持单机部署、集群部署，中小商户企业可根据业务动态扩容。unimall使用uniapp前端框架，可同时编译到 微信小程序、H5、Android App、iOS App等几个平台，可为中小商户企业节约大量维护成本。也可支撑中小商户企业前期平台横扩需求。

#### 用户端系统演示

下面分别是微信小程序包和Android安装Apk。因为苹果开发者ID太贵，所以这里没有打包，实际也可同时支持苹果的。



#### 后台端系统演示


#### Unimall 基本框架

Unimall使用前后分离的设计、后端采用Java编写，前端均适用vuejs编写。

![架构图](/snapshoot/framework.png)

#### 项目部署方式

>项目部署

##### ⓪ 服务器推荐
服务器可根据自身业务来选购，单机环境推荐2C4G

##### ① 基础运行环境

| 运行环境 | 版本号 |
|:--------|:--------|
|  MySQL   |  5.7（推荐）   |
|  JDK   |  1.8（推荐）   |
|  Redis   |  4.0.1（其他也可以）   |
|  NodeJs  |  v8.15.0(其他也可以)  |

请参考 [CentOS7.4 安装 MySQL5.7](https://github.com/iotechn/document-basic/blob/master/CentOS7.4_Install_MySQL5.7.md)

请参考 [CentOS 安装 JDK8](https://github.com/iotechn/document-basic/blob/master/CentOS_Install_JDK8.md)

请参照CentOS [安装 NodeJs 8.15.0](https://github.com/iotechn/document-basic/blob/master/CentOS_Install_NodeJS_8.15.0.md)

Redis安装可直接使用yum安装 
	
	yum install redis

安装完成后使用 redis-cli 命令，若能进入，则表示redis安装完成
##### ② 第三方SDK配置

Unimall运行需要第三方SDK，这些SDK由服务提供商提供，一般会提供其所需要的Key和Secret

| SDK | 备注 |
|:--------|:--------|
|  微信开放平台   |  App端用户微信登录、获取用户基本信息等（不做App可省略，认证微信300）   |
|  微信支付平台   |  用户微信支付付款   |
|  微信公众平台   |  小程序登录、获取用户信息等（认证微信收300）   |
|  腾讯云短信SDK   |  用户手机号注册登录时需要用到   |
|  阿里云OSS  |  阿里云对象存储，用于存储商品图片、用户评价图片等（每月免费5G流量） |
|  快递鸟SDK  |  用于快递进度查询（没有则查不到物流）  |

请确保有以下东西，就可以部署项目了

	########################################################
	### 短信SDK配置
	########################################################
	sms.enable=qcloud
	sms.qcloud.app-id=1400XXXXX
	sms.qcloud.app-key=3xxxxxxxxxxxxxxxxxxxxxxxxxxx3
	sms.qcloud.register-template-id=xxxxx
	sms.qcloud.bind-phone-template-id=xxxxxx
	sms.qcloud.reset-password-template-id=xxxxxx

	########################################################
	###OSS 文件上传配置
	########################################################
	cn.easycampus.aliyun.oss.accessId=
	cn.easycampus.aliyun.oss.accessKey=
	cn.easycampus.aliyun.oss.endpoint=
	cn.easycampus.aliyun.oss.bucket=
	cn.easycampus.aliyun.oss.callbackUrl=
	cn.easycampus.aliyun.oss.dir=
	# 此处可自定义域名
	cn.easycampus.aliyun.oss.basekUrl=https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/

	########################################################
	### 微信APP信息
	########################################################
	com.iotechn.unimall.wx.mini.app-id=
	com.iotechn.unimall.wx.mini.app-secret=
	# 微信开放平台 需要 App 才需要配置
	com.iotechn.unimall.wx.app.app-id=
	com.iotechn.unimall.wx.app.app-secret=

	########################################################
	### 微信商户信息
	########################################################
	com.iotechn.unimall.wx.mch-id=
	#自己设置的32位码
	com.iotechn.unimall.wx.mch-key=
	com.iotechn.unimall.wx.notify-url=
	com.iotechn.unimall.wx.key-path=/Users/rize/cert/1538757851_20190609_cert/apiclient_cert.p12

	########################################################
	### 快递查询配置
	########################################################
	com.iotechn.ship.query.kdn.app-key=
	com.iotechn.ship.query.kdn.business-id=


配置文件位于 /unimall-launcher/src/main/resources/application-prd.properties 将这些信息配置好，就可以了。

##### ③ 初始化数据库
首先创建一个数据库使用编码 utf8mb4

	create database unimall character set utf8mb4;

然后将sql/unimall.sql拷贝到数据库服务器上，并运行脚本

	mysql -uroot -p unimall < unimall.sql

等待执行完成，数据库初始化完毕

##### ④ 使用Maven打包项目
在这步之前，请确认电脑上安装有maven，并配有环境变量。

进入unimall项目根目录，执行

	mvn package -Dmaven.test.skip=true

打包完成后，会得到这样一个jar包。

![打包](/snapshoot/package.png)

将这个jar包复制到服务器上，并使用命令运行。

	nohup java -jar unimall-launcher-0.0.1-SNAPSHOT.jar --spring.profiles.active=prd >/dev/null &

运行完后可查看 unimall.log 文件查看日志（如果你配了的话）

到此处后端代码运行成功！