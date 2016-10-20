package com.example.wangpengyu.testdouyuface.http;

import com.example.wangpengyu.testdouyuface.baen.DouyuBean;
import com.example.wangpengyu.testdouyuface.config.DouyuConfig;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangpengyu on 16/10/19.
 * Retrofit帮助类
 */
public class RetrofitHelper {
    private static RetrofitHelper helper;
    private Retrofit retrofit;
    private RetrofitApi retrofitApi;
    private RetrofitHelper(){
        // 获得Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(DouyuConfig.BASE_URL) // 设置基础地址
                .addConverterFactory(GsonConverterFactory.create()) // 设置工厂类来造型Bean对象
                .build();
        // 通过Retrofit获得接口类实例
        retrofitApi = retrofit.create(RetrofitApi.class);
    }
    public static RetrofitHelper getInstance(){
        if (helper == null){
            synchronized (RetrofitHelper.class){
                if(helper == null){
                    helper = new RetrofitHelper();
                }
            }
        }
        return helper;
    }

    /**
     * 获得bean对象的方法
     * @param hashMap 传入参数集合
     * @param callback 回调接口
     */
    public void getBean(HashMap<String,String> hashMap, Callback<DouyuBean> callback){
        // 获得call对象
        Call<DouyuBean> bean = retrofitApi.getBean(hashMap);
        bean.enqueue(callback); // 异步开启Retrofit
    }

    public DouyuBean getBean(HashMap<String,String> hashMap){
        // 获得call对象
        Call<DouyuBean> bean = retrofitApi.getBean(hashMap);
        try {
            Response<DouyuBean> beanResponse = bean.execute();
            DouyuBean douyuBean = beanResponse.body();
            return douyuBean;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
