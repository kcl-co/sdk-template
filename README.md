# SDK-Template

> 在日常开发中，我们经常需要请求第三方提供商的接口来获取/计算数据。而当下市面上开源的`HTTP`客户端都是以面向过程的模式使用的，但这并不能充分发挥`Java`面向对象的特性，所以在这里笔者封装了一款面向对象的`HTTP`客户端，`SDK-Template`。

## 概述

`SDK-Template`是一款基于面向对象的`HTTP`客户端。与传统的`HTTP`客户端不同，`SDK-Template`的使用只需要创建`HTTP`
请求相对应的请求体和响应体即可，而不需要再创建各自的请求方法，这不论在易用性上还是在可读性上都具有明显的优势。

## 用法

在`SDK-Template`的使用上，我们只需要将请求体和响应体声明为相应的对象，然后将其交给`HttpClient`进行执行即可。下面笔者将按照这`3`部分详细展开。

### 构造`HTTP`请求

对于`HTTP`请求体的构建，我们只需要继承`SdkRequest`类，然后实现其中的`getAction()`方法、`getMethod()`方法和`getResponseClass()`方法即可，其中它们的作用如下所示：

| 方法                 | 描述      |
|--------------------|---------|
| `getAction`        | 表示请求路径。 |
| `getMethod`        | 表示请求方法。 |
| `getResponseClass` | 表示响应类型。 |

当然，如果需要在请求中添加对应的请求字段，则需要同时在`SdkRequest`的实现类中声明对应的变量，并且通过注解声明。例如：

```java
@Data
@RequiredAppId(type = QUERY_PARAMS)
@RequiredAppSecret(type = QUERY_PARAMS)
@ContentType(value = HttpConstant.ContentType.APPLICATION_JSON)
public class SdkAuthorizeRequest implements SdkRequest<SdkAuthorizeResponse> {

    @Params(name = "code", type = QUERY_PARAMS)
    private String code;

    public SdkAuthorizeRequest() {
    }

    public SdkAuthorizeRequest(String code) {
        this.code = code;
    }

    @Override
    public String getAction() {
        return "v1/authorize";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public Class<SdkAuthorizeResponse> getResponseClass() {
        return SdkAuthorizeResponse.class;
    }
}
```

在`SdkRequest`的实现类中，我们可以使用以下几个注解：

| 注解                   | 描述                            |
|----------------------|-------------------------------|
| `@ContentType`       | 表示请求`Header`中的`Content-Type`。 |
| `@RequiredAppId`     | 表示请求中需要添加`appId`字段。           |
| `@RequiredAppSecret` | 表示请求中需要添加`appSecret`字段。       |
| `@Params`            | 表示请求中需要添加的其他字段。               |

其中，在`@RequiredAppId`、`@RequiredAppSecret`和`@Params`中需要指定请求字段的类型，可选值为：

| 字段类型            | 描述              |
|-----------------|-----------------|
| `HEADER_PARAMS` | 表示`HEADER`类型参数。 |
| `QUERY_PARAMS`  | 表示`QUERY`类型参数。  |
| `BODY_PARAMS`   | 表示`BODY`类型参数。   |

这样，我们就完成了`HTTP`请求体的构建了。

### 构造`HTTP`响应

而对于`HTTP`响应体，我们则需要继承`SdkResponse`类，并且对应的具体响应类型需要在`SdkRequest`实现类中的`getResponseClass`方法进行指定。例如：

```java
@Data
public class SdkAuthorizeResponse implements SdkResponse {

    /**
     * 响应code
     */
    private Integer code;

    /**
     * 响应message
     */
    private String msg;

    /**
     * 响应数据
     */
    private String data;

}
```

这样，我们就完成了`HTTP`响应体的构建了。

### 执行`HTTP`请求

在构建完请求体和响应体后，我们就可以使用`HttpClient`的`execute`方法执行了。

```java
HttpClient httpClient=new HttpClient(endpoint,appId,appSecret);
```

```java
SdkAuthorizeResponse response=httpClient.execute(new SdkAuthorizeRequest(code));
```

这样，我们就以面向对象的方式执行了一次`HTTP`请求了。

> 更多使用细节可阅读相应的单元测试用例。

## 扩展

关于`SDK-Template`的单元测试，这里是通过 [`WireMock`](https://github.com/wiremock/wiremock) 实现模拟接收请求和响应请求的。对于`Java`代码中使用`WireMock`
，首先我们需要创建并启动服务器`WireMockServer`，然后再通过客户端`WireMock`向`WireMockServer`服务器注册`mock`映射即可。当然，我们也可以在创建`WireMockServer`服务器时，将相关的`mock`映射提前添加到它的配置中。

> 在`WireMock`的使用上，我们也可以单独将其部署为一个服务（可通过启动参数配置实例属性），然后通过`Java`客户端`WireMock`、配置文件`File`或者`HTTP`请求注册相应的`mock`映射。

最后，如果读者对`WireMock`十分感兴趣，可以阅读其官方文档和`GITHUB`源码了解更多细节：

- https://wiremock.org
- https://github.com/wiremock/wiremock

另外，对于与其具有相同作用的`MockServer`也可以了解一下：

- https://mock-server.com
- https://github.com/mock-server/mockserver
