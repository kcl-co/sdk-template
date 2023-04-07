package com.share.co.kcl.sdk.template.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpMethod {

    /**
     * POST请求
     */
    POST("POST"),

    /**
     * GET请求
     */
    GET("GET");

    private final String value;
}
