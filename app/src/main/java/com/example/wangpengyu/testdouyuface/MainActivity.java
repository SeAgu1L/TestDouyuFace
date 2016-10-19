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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        rvAdapter = new RvAdapter(this,roomBeanList);
        mRcv = ((RecyclerView)findViewById(R.id.rcy));
        mRcv.setAdapter(rvAdapter);
        mRcv.setLayoutManager(new GridLayoutManager(this,2));

    }

    private void initData() {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(DouyuConfig.Params.LIMIT,DouyuConfig.DeafaultValue.LIMIT);
        hashMap.put(DouyuConfig.Params.OFFSET,DouyuConfig.DeafaultValue.OFFSET);
        hashMap.put(DouyuConfig.Params.TIME,DouyuConfig.DeafaultValue.TIME);
        RetrofitHelper.getInstance().getBean(hashMap, new Callback<DouyuBean>() {
            @Override
            public void onResponse(Call<DouyuBean> call, Response<DouyuBean> response) {
                DouyuBean douyuBean = response.body();
                if(douyuBean != null) {
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
