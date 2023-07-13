# 设置基础镜像
FROM nginx
# 定义作者
MAINTAINER yinan
# 将dist文件中的内容复制到 /usr/share/nginx/html/ 这个目录下面
COPY dist/  /usr/share/nginx/html/
# 拷贝.conf文件到镜像下，替换掉原有的nginx.conf
COPY nginx.conf /etc/nginx/nginx.conf