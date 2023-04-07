package com.share.co.kcl.sdk.template.simple;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.matching.AnythingPattern;
import com.share.co.kcl.sdk.template.HttpClient;
import com.share.co.kcl.sdk.template.constants.HttpConstant;
import com.share.co.kcl.sdk.template.simple.model.SdkAuthorizeRequest;
import com.share.co.kcl.sdk.template.simple.model.SdkAuthorizeResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.share.co.kcl.sdk.template.simple.config.SimpleMockServerConfig.*;
import static com.share.co.kcl.sdk.template.simple.config.SimpleMockServerConfig.getEndpoint;

public class HttpClientTests {

    private static HttpClient httpClient;
    private static WireMockServer wireMockServer;

    @BeforeClass
    public static void startWireMockServer() {
        wireMockServer = new WireMockServer(
                WireMockConfiguration.options()
                        .bindAddress(HOST)
                        .port(PORT));
        wireMockServer.addStubMapping(getUserAuthorizeStubMapping());
        wireMockServer.start();
    }

    @BeforeClass
    public static void initHttpClient() {
        String endpoint = getEndpoint();
        String appId = RandomStringUtils.randomAscii(6);
        String appSecret = RandomStringUtils.randomAscii(6);
        httpClient = new HttpClient(endpoint, appId, appSecret);
    }

    @AfterClass
    public static void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    public void testSdkAuthorize() {
        SdkAuthorizeResponse response = httpClient.execute(new SdkAuthorizeRequest(RandomStringUtils.randomAscii(8)));
        Assert.assertNotNull(response.getCode());
        Assert.assertNotNull(response.getMsg());
        Assert.assertNotNull(response.getData());

        verify(1, getRequestedFor(urlMatching(".*/v1/authorize.*"))
                .withHeader("Content-Type", containing(HttpConstant.ContentType.APPLICATION_JSON))
                .withQueryParam("code", new AnythingPattern())
                .withQueryParam("appId", new AnythingPattern())
                .withQueryParam("appSecret", new AnythingPattern()));
    }
}
