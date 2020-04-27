package com.sample.uniapp.hikvisionisc;

import android.app.Application;

import com.uniapp.library.baselib.UniAppSDRLibrary;

/**
 * Created by HyFun on 2020/4/1.
 * Email: 775183940@qq.com
 * Description:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UniAppSDRLibrary.getInstance().register(this);
    }
}
