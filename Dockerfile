FROM centos:7
WORKDIR /home/dobbin
ENV RUN_ENV="prd"
RUN chmod 755 /home/dobbin/ -R

# nginx
RUN yum install epel-release -y && yum install nginx -y
COPY ./env/nginx.conf /etc/nginx/nginx.conf
COPY ./env/unimall.key /etc/nginx/ssl/unimall.key
COPY ./env/unimall.pem /etc/nginx/ssl/unimall.pem
COPY ./unimall-admin/dist /usr/share/nginx/html
RUN echo nginx >> /home/dobbin/start.sh


EXPOSE 443
EXPOSE 80

# jvm
RUN yum -y install java-1.8.0-openjdk.x86_64
COPY ./unimall-runner/target/unimall-runner-v3.jar unimall.jar

ENV JAVA_OPTS="\
-server \
-Xmx1024m \
-Xms1024m \
-Xmn512m \
-Xloggc:/home/dobbin/gc.log \
-XX:+IgnoreUnrecognizedVMOptions \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:+PrintGCDetails \
-XX:+PrintGCDateStamps \
-XX:GCLogFileSize=10M \
-XX:-UseBiasedLocking \
-XX:+UseTLAB \
-XX:+ResizeTLAB \
-XX:+PerfDisableSharedMem \
-XX:+UseCondCardMark \
-XX:CMSWaitDuration=10000 \
-XX:+UseParNewGC \
-XX:+UseConcMarkSweepGC \
-XX:+CMSParallelRemarkEnabled \
-XX:+CMSParallelInitialMarkEnabled \
-XX:+CMSEdenChunksRecordAlways \
-XX:CMSInitiatingOccupancyFraction=75 \
-XX:+UseCMSInitiatingOccupancyOnly \
-XX:+ExitOnOutOfMemoryError"
RUN echo "java \$JAVA_OPTS -Dspring.profiles.active=\${RUN_ENV} -jar unimall.jar" >> /home/dobbin/start.sh
RUN chmod +x /home/dobbin/start.sh
CMD ["sh", "-c", "/home/dobbin/start.sh" ]
