package com.share.co.kcl.sdk.template.simple.model;

import com.share.co.kcl.sdk.template.model.SdkResponse;
import lombok.Data;

@Data
public class SdkAuthorizeResponse implements SdkResponse {

    /**
     * 响应code
     */
    private Integer code;

    /**
     * 响应message
     */
    private String msg;

    /**
     * 响应数据
     */
    private String data;

}
