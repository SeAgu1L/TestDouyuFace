package com.example.wangpengyu.testdouyuface;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.litesuits.orm.LiteOrm;

/**
 * Created by wangpengyu on 16/10/19.
 */
public class App extends Application {

    public static LiteOrm liteOrm;

    @Override
    public void onCreate() {
        super.onCreate();

        initLiteOrm();
        Fresco.initialize(this);
    }

    // liteOrm保持单例
    private  void initLiteOrm() {
        if (liteOrm == null) {
            liteOrm = LiteOrm.newSingleInstance(this, "liteorm.db");
        }
    }

}
