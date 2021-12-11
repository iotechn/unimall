FROM hub.c.163.com/library/centos:7.8
WORKDIR /home/dobbin
ENV RUN_ENV="prd"

RUN sh db.sh

yum -y install nginx

yum -y install java-1.8.0-openjdk.x86_64

RUN chmod 755 /home/dobbin/ -R
COPY ./unimall-runner/target/unimall-runner-v3.jar unimall.jar
COPY ./env/nginx.conf /etc/nginx/nginx.conf
COPY ./env/unimall.key /etc/nginx/ssl/unimall.key
COPY ./env/unimall.pem /etc/nginx/ssl/unimall.pem


EXPOSE 443
EXPOSE 80

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

CMD ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=${RUN_ENV} -jar unimall.jar" ]