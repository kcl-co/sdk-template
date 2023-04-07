package com.share.co.kcl.sdk.template.demo.model;

import com.share.co.kcl.sdk.template.annotation.*;
import com.share.co.kcl.sdk.template.constants.HttpConstant;
import com.share.co.kcl.sdk.template.enums.HttpMethod;
import com.share.co.kcl.sdk.template.model.SdkRequest;
import lombok.Data;

import static com.share.co.kcl.sdk.template.enums.ParamsType.*;

@Data
@RequiredAppId(type = HEADER_PARAMS)
@RequiredAppSecret(type = HEADER_PARAMS)
@ContentType(value = HttpConstant.ContentType.APPLICATION_JSON)
public class TestUserInfoUpdateRequest implements SdkRequest<TestUserInfoUpdateResponse> {

    @Params(name = "openId", type = BODY_PARAMS)
    private String openId;

    @Params(name = "username", type = BODY_PARAMS)
    private String username;

    @Override
    public String getAction() {
        return "v1/updateUserInfo";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public Class<TestUserInfoUpdateResponse> getResponseClass() {
        return TestUserInfoUpdateResponse.class;
    }
}
