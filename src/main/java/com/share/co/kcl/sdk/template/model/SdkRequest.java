package com.share.co.kcl.sdk.template.model;

import com.share.co.kcl.sdk.template.enums.HttpMethod;

public interface SdkRequest<T extends SdkResponse> {

    /**
     * action
     */
    String getAction();

    /**
     * method
     */
    HttpMethod getMethod();

    /**
     * response class
     */
    Class<T> getResponseClass();

}
