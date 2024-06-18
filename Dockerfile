FROM registry.cn-beijing.aliyuncs.com/dobbinsoft/dobbinjdk:21
COPY ./unimall-runner/target/unimall-runner-v3.jar main.jar

ENV RUN_ENV="prod"
EXPOSE 8000

ENV JAVA_OPTS="\
-server \
-Xmx768m \
-Xms768m \
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

CMD ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=${RUN_ENV} -jar main.jar" ]
