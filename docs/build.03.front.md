> 本文讲解 编译/打包/发布 前端服务，包括 小程序、APP、H5、后台



## Vue后台

#### 打包dist

Admin使用vuejs+elementui编写。最终打包成静态文件。

首先安装项目依赖。建议使用淘宝源cnpm进行安装，速度会快很多。在unimall-admin中目录下执行命令

	cnpm install

等待安装完成依赖。配置服务器地址，编辑 unimall-admin/config/prod.env.js

	HOST: '"https://www.example.com"',
	BASE_API: '"https://www.example.com/m.api"'

将www.example.com替换为自己的域名，最后使用脚本打包

	cnpm run build:prod

最后在unimall-admin下会出现生成好的dist文件夹中。dist中包括index.html static等文件。



-- Docker

#### 放入web容器
将这些文件让入nginx的静态文件目录即可。

账号：admin
密码：1234567



## 小程序

#### 基础环境

1) 去HBuilder官方网站下载适合您操作系统的版本（需要下载App开发版）： https://www.dcloud.io/hbuilderx.html

安装Scss插件

-- Repeat



2) 下载微信Web开发者工具 https://developers.weixin.qq.com/miniprogram/dev/devtools/download.html

打开服务端口，在“设置”-“安全设置”中打开“服务端口”开关。

#### 编译发布

打开HBuilderX 在上方菜单选择 ”文件“-”导入“-”从本地目录导入“，然后选择unimall-app文件夹。

发现左侧出现项目，打开 config/.env.prod.js 文件，将baseUrl修改为自己服务器

```
baseUrl: 'https://unimall.v3.dobbinsoft.com',
```

-- Repeat



在上方菜单选择”发行“-”小程序-微信(仅适用于uni-app)“。

弹出发布菜单，输入"微信小程序名"和"微信小程序AppId"。

等待编译完成后，会自动引导微信Web开发者工具。

等待渲染完成无误后，右上方“上传”按钮。即可提交代码。