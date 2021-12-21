> 在运行jar包之前，需要先搭建应用程序依赖的运行环境。本文档是基于**CentOS7**安装的



## MySQL


下载并安装MySQL官方的 Yum Repository
在/root/目录下建立一个downloads文件夹
	
```bash
cd 
mkdir downloads
```

进去downloads并使用wget下载Yum Repository

```bash
cd downloads
wget -i -c http://dev.mysql.com/get/mysql57-community-release-el7-10.noarch.rpm
```

下载完成后，使用yum安装仓库
	
```bash
yum -y install mysql57-community-release-el7-10.noarch.rpm
```

然后使用yum安装 mysql-server
	
```bash
yum -y install mysql-community-server
```

这一步需要下载mysql，所以需要的时间比较长。

```bash
Dependency Installed:
libaio.x86_64 0:0.3.109-13.el7                                                          
mysql-community-client.x86_64 0:5.7.26-1.el7                                            
mysql-community-common.x86_64 0:5.7.26-1.el7                                            
Dependency Updated:
postfix.x86_64 2:2.10.1-7.el7                                                           
Replaced:
mariadb-libs.x86_64 1:5.5.56-2.el7                                                      
Complete!
```

看到Complete！就安装完成了，使用下面命令启动mysql

```bash
systemctl start mysqld.service
```

若启动没有报错，可使用下面命令查看状态

```bash
systemctl status mysqld.service
```

看到active (running) 表示MySQL成功启动，第一次启动mysql的时候会，mysql的默认密码会打印在启动日志里面。使用下面命令查看密码

```bash
grep "password" /var/log/mysqld.log
```

接下来使用默认密码登录
	
```bash
mysql -uroot -p
```

输入命令后，要求输入密码，此时输入默认密码按回车即可。进入mysql后，需要先修改mysql的密码。

```sql
ALTER USER 'root'@'localhost' IDENTIFIED BY '1234newpwd!@#ABC';
```

修改密码成功后用exit退出mysql，用新密码重新登录即可。到此MySQL安装完成。
	

## Redis

```bash
yum install -y redis
```



## Nginx

```bash
yum install -y nginx
```



## JDK8

```bash
yum install -y java-1.8.0-openjdk.x86_64
```



## 传输工具

```bash
yum install -y lrzsz
```

