package com.uniapp.plugin.hikvisionisc;

import android.text.TextUtils;

import com.uniapp.library.baselib.http.HttpClient;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HyFun on 2019/10/21.
 * Email: 775183940@qq.com
 * Description:
 */
class HttpService {
    private static ISCApi api;

    public static final ISCApi getService() {
        if (api == null) {
            synchronized (HttpService.class) {
                if (api == null) {
                    OkHttpClient okHttpClient = HttpClient.getInstance().getOkHttpClient();
                    okHttpClient = okHttpClient.newBuilder().addNetworkInterceptor(new JWTInterceptor()).build();
                    api = HttpClient.getInstance().createRetrofit(SDR_HIKVISION_ISC.getInstance().getServiceUrl(), okHttpClient, ISCApi.class);
                }
            }
        }
        return api;
    }


    public static class JWTInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            // 判断jwt是否为空
            if (!TextUtils.isEmpty(SDR_HIKVISION_ISC.getInstance().getAuthorization())) {
                request = request.newBuilder().addHeader("Authorization", SDR_HIKVISION_ISC.getInstance().getAuthorization()).build();
            }
            return chain.proceed(request);
        }
    }
}
