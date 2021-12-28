# 安装JDK
cd
mkdir downloads
cd downloads
wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u141-b15/336fa29ff2bb4ef291e347e091f7f4a7/jdk-8u141-linux-x64.tar.gz"
tar -zxvf jdk-8u141-linux-x64.tar.gz
mv jdk1.8.0_141/ ../
cd
ln -s jdk1.8.0_141/ jdk
echo "export JAVA_HOME='/root/jdk'" >> .bashrc
echo "export PATH=\$PATH:\$JAVA_HOME/bin" >> .bashrc


# 安装mvn
cd
cd downloads
wget https://dlcdn.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz --no-check-certificate
tar -zxvf apache-maven-3.8.4-bin.tar.gz
mv apache-maven-3.8.4 ../
cd
ln -s apache-maven-3.8.4/ maven

echo "export MAVEN_HOME='/root/maven'" >> .bashrc
echo "export PATH=\$PATH:\$MAVEN_HOME/bin" >> .bashrc


# 安装npm
cd
cd downloads
wget https://npmmirror.com/mirrors/node/v16.13.1/node-v16.13.1-linux-x64.tar.xz
xz -d node-v16.13.1-linux-x64.tar.xz
tar -xvf node-v16.13.1-linux-x64.tar
mv node-v16.13.1-linux-x64 ../
cd
ln -s node-v16.13.1-linux-x64/ node
echo "export NODE_HOME='/root/node'" >> .bashrc
echo "export PATH=\$PATH:\$NODE_HOME/bin" >> .bashrc

source .bashrc

npm install -g cnpm --registry=https://registry.npm.taobao.org
