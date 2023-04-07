package com.share.co.kcl.sdk.template.demo;

import com.share.co.kcl.sdk.template.demo.model.TestResponse;
import org.apache.commons.lang3.RandomStringUtils;

public class HttpClientTestsHelper {

    public static final Integer SUCCESS_CODE = 0;
    public static final String SUCCESS_MSG = "SUCCESS";

    public static final String SERVER_APP_ID = RandomStringUtils.randomAscii(8);
    public static final String SERVER_APP_SECRET = RandomStringUtils.randomAscii(8);

    private HttpClientTestsHelper() {
    }

    public static <T> TestResponse<T> buildSuccessResponse(T data) {
        TestResponse<T> sdkResponse = new TestResponse<>();
        sdkResponse.setCode(SUCCESS_CODE);
        sdkResponse.setMsg(SUCCESS_MSG);
        sdkResponse.setData(data);
        return sdkResponse;
    }


}
