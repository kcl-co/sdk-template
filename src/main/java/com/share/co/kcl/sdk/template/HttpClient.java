package com.share.co.kcl.sdk.template;

import com.alibaba.fastjson2.JSON;
import com.share.co.kcl.sdk.template.annotation.*;
import com.share.co.kcl.sdk.template.constants.HttpConstant;
import com.share.co.kcl.sdk.template.exception.SystemException;
import com.share.co.kcl.sdk.template.model.SdkRequest;
import com.share.co.kcl.sdk.template.model.SdkResponse;
import com.share.co.kcl.sdk.template.enums.ParamsType;
import com.share.co.kcl.sdk.template.utils.HttpUtils;
import com.share.co.kcl.sdk.template.utils.ReflectUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

public class HttpClient {

    private final String endpoint;
    private final String appId;
    private final String appSecret;

    public HttpClient(String endpoint, String appId, String appSecret) {
        this.endpoint = endpoint;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public <T extends SdkResponse> T execute(SdkRequest<T> request) {
        Map<String, String> headerParams = this.computeHeaderParams(request);
        Map<String, String> queryParams = this.computeQueryParams(request);
        Map<String, Object> bodyParams = this.computeBodyParams(request);
        String uri = HttpUtils.constructURI(this.endpoint, request.getAction(), queryParams);
        switch (request.getMethod()) {
            case POST: {
                String result = HttpUtils.doPost(uri, headerParams, bodyParams);
                return JSON.parseObject(result, request.getResponseClass());
            }
            case GET: {
                String result = HttpUtils.doGet(uri, headerParams, Collections.emptyMap());
                return JSON.parseObject(result, request.getResponseClass());
            }
            default:
                throw new SystemException("暂不支持请求类型");
        }
    }

    private Map<String, String> computeHeaderParams(SdkRequest<?> sdkRequest) {
        Map<String, String> result = new HashMap<>();

        List<Field> fieldList = ReflectUtils.getFieldFromClassSet(Collections.singleton(sdkRequest.getClass()));
        for (Field field : fieldList) {
            String fieldName = field.getName();
            if (HttpConstant.EXCLUDE_PARAMS_FIELDS.contains(fieldName)) {
                continue;
            }
            Params params = field.getAnnotation(Params.class);
            if (Objects.nonNull(params) && ParamsType.HEADER_PARAMS.equals(params.type())) {
                String key = StringUtils.isNotBlank(params.name()) ? params.name() : fieldName;
                Object value = ReflectUtils.invokeGetMethod(sdkRequest, field.getName());
                result.put(key, Optional.ofNullable(value).map(String::valueOf).orElse(StringUtils.EMPTY));
            }
        }

        RequiredAppId requiredAppId = sdkRequest.getClass().getAnnotation(RequiredAppId.class);
        if (requiredAppId != null && ParamsType.HEADER_PARAMS.equals(requiredAppId.type())) {
            result.put(requiredAppId.name(), this.appId);
        }

        RequiredAppSecret requiredAppSecret = sdkRequest.getClass().getAnnotation(RequiredAppSecret.class);
        if (requiredAppSecret != null && ParamsType.HEADER_PARAMS.equals(requiredAppSecret.type())) {
            result.put(requiredAppSecret.name(), this.appSecret);
        }

        ContentType contentType = sdkRequest.getClass().getAnnotation(ContentType.class);
        if (contentType != null && StringUtils.isNotBlank(contentType.value())) {
            result.put(contentType.name(), contentType.value());
        }

        return result;
    }

    private Map<String, String> computeQueryParams(SdkRequest<?> sdkRequest) {
        Map<String, String> result = new HashMap<>();

        List<Field> fieldList = ReflectUtils.getFieldFromClassSet(Collections.singleton(sdkRequest.getClass()));
        for (Field field : fieldList) {
            String fieldName = field.getName();
            if (HttpConstant.EXCLUDE_PARAMS_FIELDS.contains(fieldName)) {
                continue;
            }
            Params params = field.getAnnotation(Params.class);
            if (Objects.nonNull(params) && ParamsType.QUERY_PARAMS.equals(params.type())) {
                String key = StringUtils.isNotBlank(params.name()) ? params.name() : fieldName;
                Object value = ReflectUtils.invokeGetMethod(sdkRequest, field.getName());
                result.put(key, Optional.ofNullable(value).map(String::valueOf).orElse(StringUtils.EMPTY));
            }
        }

        RequiredAppId requiredAppId = sdkRequest.getClass().getAnnotation(RequiredAppId.class);
        if (requiredAppId != null && ParamsType.QUERY_PARAMS.equals(requiredAppId.type())) {
            result.put(requiredAppId.name(), this.appId);
        }

        RequiredAppSecret requiredAppSecret = sdkRequest.getClass().getAnnotation(RequiredAppSecret.class);
        if (requiredAppSecret != null && ParamsType.QUERY_PARAMS.equals(requiredAppSecret.type())) {
            result.put(requiredAppSecret.name(), this.appSecret);
        }

        return result;
    }

    private Map<String, Object> computeBodyParams(SdkRequest<?> sdkRequest) {
        Map<String, Object> result = new HashMap<>();

        List<Field> fieldList = ReflectUtils.getFieldFromClassSet(Collections.singleton(sdkRequest.getClass()));
        for (Field field : fieldList) {
            String fieldName = field.getName();
            if (HttpConstant.EXCLUDE_PARAMS_FIELDS.contains(fieldName)) {
                continue;
            }
            Params params = field.getAnnotation(Params.class);
            if (Objects.nonNull(params) && ParamsType.BODY_PARAMS.equals(params.type())) {
                String key = StringUtils.isNotBlank(params.name()) ? params.name() : fieldName;
                Object value = ReflectUtils.invokeGetMethod(sdkRequest, field.getName());
                result.put(key, value);
            }
        }

        RequiredAppId requiredAppId = sdkRequest.getClass().getAnnotation(RequiredAppId.class);
        if (requiredAppId != null && ParamsType.BODY_PARAMS.equals(requiredAppId.type())) {
            result.put(requiredAppId.name(), this.appId);
        }

        RequiredAppSecret requiredAppSecret = sdkRequest.getClass().getAnnotation(RequiredAppSecret.class);
        if (requiredAppSecret != null && ParamsType.BODY_PARAMS.equals(requiredAppSecret.type())) {
            result.put(requiredAppSecret.name(), this.appSecret);
        }

        return result;
    }
}
