package com.share.co.kcl.sdk.template.demo.model;

import com.share.co.kcl.sdk.template.annotation.*;
import com.share.co.kcl.sdk.template.constants.HttpConstant;
import com.share.co.kcl.sdk.template.enums.HttpMethod;
import com.share.co.kcl.sdk.template.model.SdkRequest;
import lombok.Data;

import static com.share.co.kcl.sdk.template.enums.ParamsType.*;

@Data
@RequiredAppId(type = QUERY_PARAMS)
@RequiredAppSecret(type = QUERY_PARAMS)
@ContentType(value = HttpConstant.ContentType.APPLICATION_JSON)
public class TestUserInfoGetRequest implements SdkRequest<TestUserInfoGetResponse> {

    @Params(name = "openId", type = QUERY_PARAMS)
    private String openId;

    public TestUserInfoGetRequest() {
    }

    public TestUserInfoGetRequest(String openId) {
        this.openId = openId;
    }

    @Override
    public String getAction() {
        return "v1/getUserInfo";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public Class<TestUserInfoGetResponse> getResponseClass() {
        return TestUserInfoGetResponse.class;
    }
}
