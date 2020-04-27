package com.uniapp.plugin.hikvisionisc;

import android.content.Context;

import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.annotation.JSMethod;

/**
 * Created by HyFun on 2020/3/27.
 * Email: 775183940@qq.com
 * Description:
 */
public class UniAppWXModule extends WXSDKEngine.DestroyableModule {
    // 开启activity

    @JSMethod(uiThread = true)
    public void open(String serviceUrl, String authorization) {
        final Context context = mWXSDKInstance.getContext();
        SDR_HIKVISION_ISC.getInstance().start(context, serviceUrl, authorization);
    }

    @Override
    public void destroy() {
    }
}
