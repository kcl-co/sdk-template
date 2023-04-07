package com.share.co.kcl.sdk.template.constants;

import java.util.Arrays;
import java.util.List;

public class HttpConstant {

    /**
     * 过滤字段
     */
    public static final List<String> EXCLUDE_PARAMS_FIELDS = Arrays.asList("action", "method");

    private HttpConstant() {
    }

    public static class ContentType {
        
        public static final String WILDCARD = "*/*";
        public static final String TEXT_PLAIN = "text/plain";
        public static final String TEXT_HTML = "text/html";
        public static final String TEXT_XML = "text/xml";
        public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
        public static final String APPLICATION_JSON = "application/json";
        public static final String APPLICATION_XML = "application/xml";
        public static final String MULTIPART_FORM_DATA = "multipart/form-data";

        private ContentType() {
        }
    }
}
