package com.example.wangpengyu.testdouyuface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wangpengyu.testdouyuface.adapter.RvAdapter;
import com.example.wangpengyu.testdouyuface.baen.DouyuBean;
import com.example.wangpengyu.testdouyuface.baen.RoomBean;
import com.example.wangpengyu.testdouyuface.config.DouyuConfig;
import com.example.wangpengyu.testdouyuface.http.RetrofitHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private List<RoomBean> roomBeanList = new ArrayList<>();

    private RvAdapter rvAdapter;

    private RecyclerView mRcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRcv();
        initData();
    }

    private void initRcv() {
        rvAdapter = new RvAdapter(this, roomBeanList);
        mRcv = ((RecyclerView) findViewById(R.id.rcy));
        mRcv.setAdapter(rvAdapter);
        mRcv.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void initData() {
        // 参数
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(DouyuConfig.Params.LIMIT, DouyuConfig.DeafaultValue.LIMIT);
        hashMap.put(DouyuConfig.Params.OFFSET, DouyuConfig.DeafaultValue.OFFSET);
        hashMap.put(DouyuConfig.Params.TIME, DouyuConfig.DeafaultValue.TIME);

        // 创建被观察者和监听器
        Observable.create(new Observable.OnSubscribe<DouyuBean>() {
            @Override
            public void call(Subscriber<? super DouyuBean> subscriber) {
                DouyuBean douyuBean = RetrofitHelper.getInstance().getBean(hashMap);
                if (douyuBean == null) {
                    // 如果douyuBean为空则发出异常
                    subscriber.onError(new IOException());
                } else {
                    // 如果douyuBean不为空则发送给观察者
                    subscriber.onNext(douyuBean);
                }
            }
        })
                .subscribeOn(Schedulers.io()) // 设置被观察者运行线程
                .observeOn(AndroidSchedulers.mainThread()) // 设置观察者运行线程
                .subscribe( // 设置观察者
                        new Subscriber<DouyuBean>() {
                            // 监听完成
                            @Override
                            public void onCompleted() {

                            }
                            // 发生异常
                            @Override
                            public void onError(Throwable e) {

                            }
                            // 接收
                            @Override
                            public void onNext(DouyuBean douyuBean) {
                                List<RoomBean> roomBeens = douyuBean.getData();
                                roomBeanList.addAll(roomBeens);
                                if (roomBeens != null && roomBeens.size() != 0) {
                                    ((App) getApplication()).liteOrm.save(roomBeens);
                                }
                                rvAdapter.notifyDataSetChanged();
                            }
                        }
                );


    }

    private void getBeanEnqueue(HashMap<String, String> hashMap) {
        RetrofitHelper.getInstance().getBean(hashMap, new Callback<DouyuBean>() {
            @Override
            public void onResponse(Call<DouyuBean> call, Response<DouyuBean> response) {
                DouyuBean douyuBean = response.body();
                if (douyuBean != null) {
                    List<RoomBean> roomBeens = douyuBean.getData();
                    roomBeanList.addAll(roomBeens);
                    if (roomBeens != null && roomBeens.size() != 0) {
                        ((App) getApplication()).liteOrm.save(roomBeens);
                    }
                    rvAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<DouyuBean> call, Throwable t) {

            }
        });
    }
}
