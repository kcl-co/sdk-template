package com.share.co.kcl.sdk.template.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TestUserInfoGetResponse extends TestResponse<TestUserInfoGetResponse.ResultData> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultData {

        private String openId;

        private String username;

    }
}
