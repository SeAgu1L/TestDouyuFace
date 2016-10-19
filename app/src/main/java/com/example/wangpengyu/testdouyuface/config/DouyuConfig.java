package com.example.wangpengyu.testdouyuface.config;

/**
 * Created by wangpengyu on 16/10/19.
 */
public class DouyuConfig {
    public static final String BASE_URL = "http://capi.douyucdn.cn/";
    public static class Path{
        public static final String FACE = "api/v1/getVerticalRoom";
    }
    public static class Params{
        public static final String LIMIT = "limit";
        public static final String OFFSET = "offset";
        public static final String TIME = "time";
    }
    public static class DeafaultValue{
        public static final String LIMIT = "20";
        public static final String OFFSET = "0";
        public static final String TIME = "1470800460";
    }
}
