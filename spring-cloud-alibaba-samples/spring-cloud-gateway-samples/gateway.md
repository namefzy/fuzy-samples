# SpringCloud

## Gateway
### 简介

> Spring Cloud Gateway 是Spring Cloud的一个全新的API网关项目，目的是为了替换掉Zuul1。Gateway可以与Spring Cloud Discovery Client（如Eureka）、Ribbon、Hystrix等组件配合使用，实现路由转发、负载均衡、熔断等功能，并且Gateway还内置了限流过滤器，实现了限流的功能。
> 

`SpringCloud Gateway`有以下几大核心概念需要了解：

- Route（路由）

  > Route 是网关的基础元素，由 ID、目标 URI、断言、过滤器组成。当请求到达网关时，由 Gateway Handler Mapping 通过断言进行路由匹配（Mapping），当断言为真时，匹配到路由。

- Predicate（断言）

  > Predicate 是 [Java](http://c.biancheng.net/java/) 8 中提供的一个函数。输入类型是`Spring Framework ServerWebExchange`。它允许开发人员匹配来自 HTTP 的请求，例如请求头或者请求参数。**简单来说它就是匹配条件**。

- Filter（过滤器）

  > 可以使用它拦截和修改请求，并且对上游的响应，进行二次处理。过滤器为`org.springframework.cloud.gateway.filter.GatewayFilter`类的实例。