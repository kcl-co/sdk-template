package com.share.co.kcl.sdk.template.demo.config;

import com.alibaba.fastjson2.JSON;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.AnythingPattern;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.share.co.kcl.sdk.template.constants.HttpConstant.*;
import com.share.co.kcl.sdk.template.demo.HttpClientTestsHelper;
import com.share.co.kcl.sdk.template.demo.model.TestAuthorizeResponse;
import com.share.co.kcl.sdk.template.demo.model.TestResponse;
import com.share.co.kcl.sdk.template.demo.model.TestUserInfoGetResponse;
import org.apache.commons.lang3.RandomStringUtils;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class DemoMockServerConfig {

    public static final String PROTOCOL = "http";

    public static final String HOST = "127.0.0.1";

    public static final Integer PORT = 8080;

    private DemoMockServerConfig() {
    }

    public static String getEndpoint() {
        return PROTOCOL + "://" + HOST + ":" + PORT;
    }

    public static StubMapping getUserAuthorizeStubMapping() {
        String openId = RandomStringUtils.randomAscii(16);
        TestAuthorizeResponse.ResultData resultData = new TestAuthorizeResponse.ResultData(openId);
        TestResponse<TestAuthorizeResponse.ResultData> sdkResponse = HttpClientTestsHelper.buildSuccessResponse(resultData);
        return get(urlMatching(".*/v1/authorize.*"))
                .withHeader("Content-Type", containing(ContentType.APPLICATION_JSON))
                .withQueryParam("code", new AnythingPattern())
                .withQueryParam("appId", equalTo(HttpClientTestsHelper.SERVER_APP_ID))
                .withQueryParam("appSecret", equalTo(HttpClientTestsHelper.SERVER_APP_SECRET))
                .willReturn(WireMock.ok()
                        .withHeader("Content-Type", ContentType.APPLICATION_JSON)
                        .withBody(JSON.toJSONString(sdkResponse)))
                .build();
    }

    public static StubMapping getUserInfoGetStubMapping() {
        String openId = RandomStringUtils.randomAscii(16);
        String username = RandomStringUtils.randomAscii(8);
        TestUserInfoGetResponse.ResultData resultData = new TestUserInfoGetResponse.ResultData(openId, username);
        TestResponse<TestUserInfoGetResponse.ResultData> sdkResponse = HttpClientTestsHelper.buildSuccessResponse(resultData);
        return get(urlMatching(".*/v1/getUserInfo.*"))
                .withHeader("Content-Type", containing(ContentType.APPLICATION_JSON))
                .withQueryParam("openId", new AnythingPattern())
                .withQueryParam("appId", equalTo(HttpClientTestsHelper.SERVER_APP_ID))
                .withQueryParam("appSecret", equalTo(HttpClientTestsHelper.SERVER_APP_SECRET))
                .willReturn(WireMock.ok()
                        .withHeader("Content-Type", ContentType.APPLICATION_JSON)
                        .withBody(JSON.toJSONString(sdkResponse)))
                .build();
    }

    public static StubMapping getUserInfoUpdateStubMapping() {
        TestResponse<Boolean> sdkResponse = HttpClientTestsHelper.buildSuccessResponse(Boolean.TRUE);
        return post(urlMatching(".*/v1/updateUserInfo.*"))
                .withHeader("Content-Type", containing(ContentType.APPLICATION_JSON))
                .withHeader("appId", equalTo(HttpClientTestsHelper.SERVER_APP_ID))
                .withHeader("appSecret", equalTo(HttpClientTestsHelper.SERVER_APP_SECRET))
                .willReturn(WireMock.ok()
                        .withHeader("Content-Type", ContentType.APPLICATION_JSON)
                        .withBody(JSON.toJSONString(sdkResponse)))
                .build();
    }

}
