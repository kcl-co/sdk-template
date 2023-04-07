package com.share.co.kcl.sdk.template.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TestAuthorizeResponse extends TestResponse<TestAuthorizeResponse.ResultData> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultData {
        private String openId;
    }
}
