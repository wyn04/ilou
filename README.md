# 爱楼-表情包网站

## 需求分析

1. 用户根据名字和标签搜索表情包
2. 用户上传表情包
3. 管理员管理表情包

## 技术栈

### 前端

- 框架：Vue 3
- 组件库：Ant Design Vue
- 请求：Axios
- 路由：Vue Router
- 文件下载：FileSaver

### 后端

- Java 8
- 开发框架：SpringBoot 2.x
- 数据访问：MyBatis + MyBatis Plus
- 缓存：Redis
- 项目管理：Maven
- 接口文档：Swagger + Knife4j

### 存储

- 数据库：MySQL
- 对象存储：七牛云 COS

### 部署

- 前端：Nginx + Docker
- 后端：Docker容器

## 特性

1. 使用 Redis 缓存首页高频访问的表情包列表，将接口响应时长从120毫秒缩短至 20毫秒。  
2. 选用 MyBatis + MyBatis-Plus 进行数据访问层开发，复用大多数通用方法，并且通过继承定制了自己的通用操作模板 ，大幅提升了项目开发效率。  
3. 为了明确接口的返回，自定义统一的错误码，并封装了全局异常处理器 ，从而规范了异常返回、屏蔽了项目冗余的报错细节。  
4. 自主编写 Dockerfile，并通过容器部署，提高部署上线效率。  
5. 使用 Knife4j + Swagger 自动生成后端接口文档，避免了人工编写维护文档的麻烦。  
6. 前端使用 Vant UI 组件库，并封装了全局通用的 Layout 组件，使主页、搜索页、管理页布局一致、并减少重复代码。  

## 软件设计

### 数据库设计

**数据库名：** ilou

| 表名            | 说明 |
| :-------------- | :--- |
| [emoji](#emoji) | 表情 |
| [tag](#tag)     | 标签 |

**表名：** <a id="tag">emoji</a>

**说明：** 表情

**数据列：**

| 序号 | 名称         | 数据类型 | 长度 | 小数位 | 允许空值 | 主键 |      默认值       |           说明           |
| :--: | :----------- | :------: | :--: | :----: | :------: | :--: | :---------------: | :----------------------: |
|  1   | id           |   int    |  10  |   0    |    N     |  Y   |                   |                          |
|  2   | url          | varchar  | 4096 |   0    |    Y     |  N   |                   |                          |
|  3   | name         | varchar  | 512  |   0    |    Y     |  N   |                   |                          |
|  4   | userId       |   int    |  10  |   0    |    Y     |  N   |         0         |        上传用户id        |
|  5   | tags         | varchar  | 1024 |   0    |    Y     |  N   |                   |       标签数组json       |
|  6   | reviewStatus |   int    |  10  |   0    |    N     |  N   |         0         |  0-待审核,1-通过,2-拒绝  |
|  7   | createTime   | datetime |  19  |   0    |    Y     |  N   | CURRENT_TIMESTAMP |                          |
|  8   | isDelete     | tinyint  |  3   |   0    |    N     |  N   |         0         | 是否删除0-未删除1-已删除 |
|  9   | updateTime   | datetime |  19  |   0    |    Y     |  N   | CURRENT_TIMESTAMP |                          |

**表名：** <a id="tag">tag</a>

**说明：** 标签

**数据列：**

| 序号 | 名称       | 数据类型 | 长度 | 小数位 | 允许空值 | 主键 |      默认值       |           说明           |
| :--: | :--------- | :------: | :--: | :----: | :------: | :--: | :---------------: | :----------------------: |
|  1   | id         |   int    |  10  |   0    |    N     |  Y   |                   |                          |
|  2   | name       | varchar  | 512  |   0    |    N     |  N   |                   |          标签名          |
|  3   | createTime | datetime |  19  |   0    |    Y     |  N   | CURRENT_TIMESTAMP |                          |
|  4   | isDelete   | tinyint  |  3   |   0    |    N     |  N   |         0         | 是否删除0-未删除1-已删除 |
|  5   | updateTime | datetime |  19  |   0    |    Y     |  N   | CURRENT_TIMESTAMP |                          |


### 通用返回对象

给对象补充一些信息，告诉前端这个请求在业务层面上是成功还是失败

```java
SUCCESS(0, "ok"),
PARAMS_ERROR(40000, "请求参数错误"),
NOT_LOGIN_ERROR(40100, "未登录"),
NO_AUTH_ERROR(40101, "无权限"),
NOT_FOUND_ERROR(40400, "请求数据不存在"),
FORBIDDEN_ERROR(40300, "禁止访问"),
SYSTEM_ERROR(50000, "系统内部异常"),
OPERATION_ERROR(50001, "操作失败"),
FILE_UPLOAD_ERROR(50010, "文件上传错误");
```

### 全局异常处理器

1. 定义业务异常类

   1. 相对于 java 的异常类，支持更多字段
   2. 自定义构造函数，更灵活 / 快捷的设置字段
2. 编写全局异常处理器（利用 Spring AOP，在调用方法前后进行额外的处理）

