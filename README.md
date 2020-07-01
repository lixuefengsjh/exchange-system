本项目预期采用springCloud+Spring Cloud Alibaba分布式架构
(1)第一期将完善所有登陆相关模块功能：图片认证，短信认证，邮箱认证，第三方登陆(QQ,WECHART)
(2)第二期在现有基础完善权限管理模块，主要是人员，角色，权限相关功能完善
(3)第三期项目逐渐升级改造为分布式架构
升级后，项目将对springCloud所有技术栈有所涉及
Eureka ---->nacos
Feign ---->openFeign
Hystrix --->sentinel
Zuul ---->GateWay
Configure+bus ----->nacos
分布式事务采用seata管理
(4)第四期：优化完善后端，以及加入前端，app相关模块，逐渐加入消息队列，缓存，高并发等相关应用场景。
