# Docker image for springboot application
# VERSION 0.0.1
# Author: gmy

### 基础镜像
FROM java:8

#作者
MAINTAINER gmy <1508594767@qq.com>

#系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

#声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME /tmp

#应用构建成功后的jar文件被复制到镜像内，名字也改成了blog.jar
ADD target/blog-api-0.0.1.jar blog.jar

#启动容器时的进程
# "-Djava.security.egd=file:/dev/urandom": 加快随机数产生过程
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar","/blog.jar"]

#暴露8000端口
EXPOSE 8000
