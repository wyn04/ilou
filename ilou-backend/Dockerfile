# Docker image for springboot file run
# VERSION 0.0.1
# Author: yian
# 基础镜像使用java
FROM openjdk:8
# 作者
MAINTAINER yinan
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目并录下创建了一个临时文件，链接到容器的/tmp
VOLUME /tmp 
# 将jar包添加到容器中并更名为xx.jar
ADD ilou-backend-0.0.1-SNAPSHOT.jar ilou-backend.jar
# 运行jar包
RUN bash -c 'touch /ilou-backend.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ilou-backend.jar"]