package com.share.co.kcl.sdk.template.simple.model;

import com.share.co.kcl.sdk.template.annotation.ContentType;
import com.share.co.kcl.sdk.template.annotation.Params;
import com.share.co.kcl.sdk.template.annotation.RequiredAppId;
import com.share.co.kcl.sdk.template.annotation.RequiredAppSecret;
import com.share.co.kcl.sdk.template.constants.HttpConstant;
import com.share.co.kcl.sdk.template.enums.HttpMethod;
import com.share.co.kcl.sdk.template.model.SdkRequest;
import lombok.Data;

import static com.share.co.kcl.sdk.template.enums.ParamsType.QUERY_PARAMS;

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
