package com.example.wangpengyu.testdouyuface.http;

import com.example.wangpengyu.testdouyuface.baen.DouyuBean;
import com.example.wangpengyu.testdouyuface.config.DouyuConfig;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by wangpengyu on 16/10/19.
 * Retrofit接口
 */
public interface RetrofitApi {
    /**
     * 抽象方法
     * @param hashMap 传入参数
     * @return
     */
    @GET(DouyuConfig.Path.FACE)
    Call<DouyuBean> getBean(@QueryMap HashMap<String ,String> hashMap);
}
