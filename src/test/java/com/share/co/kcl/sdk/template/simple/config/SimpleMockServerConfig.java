package com.share.co.kcl.sdk.template.simple.config;

import com.alibaba.fastjson2.JSON;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.AnythingPattern;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.share.co.kcl.sdk.template.constants.HttpConstant.ContentType;
import com.share.co.kcl.sdk.template.simple.model.SdkAuthorizeResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class SimpleMockServerConfig {

    public static final String PROTOCOL = "http";

    public static final String HOST = "127.0.0.1";

    public static final Integer PORT = 8080;

    private SimpleMockServerConfig() {
    }

    public static String getEndpoint() {
        return PROTOCOL + "://" + HOST + ":" + PORT;
    }

    public static StubMapping getUserAuthorizeStubMapping() {
        SdkAuthorizeResponse response = new SdkAuthorizeResponse();
        response.setCode(RandomUtils.nextInt());
        response.setMsg(RandomStringUtils.randomAscii(6));
        response.setData(RandomStringUtils.randomAscii(16));
        return get(urlMatching(".*/v1/authorize.*"))
                .withHeader("Content-Type", containing(ContentType.APPLICATION_JSON))
                .withQueryParam("code", new AnythingPattern())
                .withQueryParam("appId", new AnythingPattern())
                .withQueryParam("appSecret", new AnythingPattern())
                .willReturn(WireMock.ok()
                        .withHeader("Content-Type", ContentType.APPLICATION_JSON)
                        .withBody(JSON.toJSONString(response)))
                .build();
    }

}
