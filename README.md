# KMall

[![Build Status](https://www.travis-ci.org/kaaass/kmall.svg?branch=master)](https://www.travis-ci.org/kaaass/kmall)

一个由 SpringBoot + Hibernate 构建的简易商城。

本项目为吉林大学软件学院面向对象课程设计荣誉课程(2018级)课程项目的代码仓库。

本项目的开发、测试环境均为Linux（Manjaro）。

## 使用

### 准备工作

1. 安装MariaDB、RabbitMQ、Redis
2. 将`src/resources/application.yml.sample`文件名修改为`application.yml`，并根据注释配置数据库连接、Druid、Redis、RabbitMQ等。

### 编译

```shell
./mvnw clean compile
./mvnw clean package
```

## 项目特点

- 前后端分离设计，后端接口RESTful
- 使用RabbitMQ维护订单处理队列
- 使用事件系统驱动部分控制逻辑
- 使用元数据维护用户、商品附加信息
- 允许使用JavaScript插件扩充功能
- 打折策略采用流式处理、可以通过反射控制、可以使用JavaScript设计打折策略
- 使用Redis进行数据缓存
- 前端使用jQuery + Handlebars + RequireJS + Axios

## 致谢

https://github.com/MinecraftForge/MinecraftForge

## 未完成部分

- [ ] 参数校验
- [ ] 后台管理页面
- [ ] 丰富注释、文档、单元测试
