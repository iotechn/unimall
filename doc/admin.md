### Admin

> Admin就是App的管理后台，管理商品、订单、推广等。Admin部署前，请确认已经安装NPM。

##### ① 打包dist

Admin使用vuejs+elementui编写。最终打包成静态文件。

首先安装项目依赖。建议使用淘宝源cnpm进行安装，速度会快很多。在unimall-admin中目录下执行命令

	cnpm install

等待安装完成依赖。配置服务器地址，编辑 unimall-admin/config/prod.env.js

	HOST: '"https://www.example.com"',
	BASE_API: '"https://www.example.com/m.api"'

将www.example.com替换为自己的域名，最后使用脚本打包

	cnpm run build:prod

最后在unimall-admin下会出现生成好的dist文件夹中。dist中包括index.html static等文件。

##### ② 放入web容器
将这些文件让入nginx的静态文件目录即可。

账号：admin
密码：1234567