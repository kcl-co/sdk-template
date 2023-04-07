package com.share.co.kcl.sdk.template.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.share.co.kcl.sdk.template.HttpClient;
import com.share.co.kcl.sdk.template.constants.HttpConstant;
import com.share.co.kcl.sdk.template.demo.model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.share.co.kcl.sdk.template.demo.config.DemoMockServerConfig.*;
import static com.share.co.kcl.sdk.template.demo.HttpClientTestsHelper.*;

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
        wireMockServer.addStubMapping(getUserInfoGetStubMapping());
        wireMockServer.addStubMapping(getUserInfoUpdateStubMapping());
        wireMockServer.start();
    }

    @BeforeClass
    public static void initHttpClient() {
        httpClient = new HttpClient(getEndpoint(), SERVER_APP_ID, SERVER_APP_SECRET);
    }

    @AfterClass
    public static void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    public void testAuthorize() {
        String code = RandomStringUtils.randomAscii(8);
        TestAuthorizeResponse response = httpClient.execute(new TestAuthorizeRequest(code));
        Assert.assertEquals(HttpClientTestsHelper.SUCCESS_CODE, response.getCode());
        Assert.assertEquals(HttpClientTestsHelper.SUCCESS_MSG, response.getMsg());
        Assert.assertNotNull(response.getData());
        Assert.assertNotNull(response.getData().getOpenId());

        verify(1, getRequestedFor(urlMatching(".*/v1/authorize.*"))
                .withHeader("Content-Type", containing(HttpConstant.ContentType.APPLICATION_JSON))
                .withQueryParam("code", equalTo(code))
                .withQueryParam("appId", equalTo(HttpClientTestsHelper.SERVER_APP_ID))
                .withQueryParam("appSecret", equalTo(HttpClientTestsHelper.SERVER_APP_SECRET)));
    }

    @Test
    public void testGetUserInfo() {
        String openId = RandomStringUtils.randomAscii(8);
        TestUserInfoGetResponse response = httpClient.execute(new TestUserInfoGetRequest(openId));
        Assert.assertEquals(HttpClientTestsHelper.SUCCESS_CODE, response.getCode());
        Assert.assertEquals(HttpClientTestsHelper.SUCCESS_MSG, response.getMsg());
        Assert.assertNotNull(response.getData());
        Assert.assertNotNull(response.getData().getOpenId());
        Assert.assertNotNull(response.getData().getUsername());

        verify(1, getRequestedFor(urlMatching(".*/v1/getUserInfo.*"))
                .withHeader("Content-Type", containing(HttpConstant.ContentType.APPLICATION_JSON))
                .withQueryParam("openId", equalTo(openId))
                .withQueryParam("appId", equalTo(HttpClientTestsHelper.SERVER_APP_ID))
                .withQueryParam("appSecret", equalTo(HttpClientTestsHelper.SERVER_APP_SECRET)));
    }

    @Test
    public void testUpdateUserInfo() {
        TestUserInfoUpdateRequest request = new TestUserInfoUpdateRequest();
        request.setOpenId(RandomStringUtils.randomAscii(8));
        request.setUsername(RandomStringUtils.randomAscii(6));
        TestUserInfoUpdateResponse response = httpClient.execute(request);
        Assert.assertEquals(HttpClientTestsHelper.SUCCESS_CODE, response.getCode());
        Assert.assertEquals(HttpClientTestsHelper.SUCCESS_MSG, response.getMsg());
        Assert.assertNotNull(response.getData());

        verify(1, postRequestedFor(urlMatching(".*/v1/updateUserInfo.*"))
                .withHeader("Content-Type", containing(HttpConstant.ContentType.APPLICATION_JSON))
                .withHeader("appId", equalTo(HttpClientTestsHelper.SERVER_APP_ID))
                .withHeader("appSecret", equalTo(HttpClientTestsHelper.SERVER_APP_SECRET)));
    }


}
