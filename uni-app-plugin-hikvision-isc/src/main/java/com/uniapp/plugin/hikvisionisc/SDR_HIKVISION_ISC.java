package com.uniapp.plugin.hikvisionisc;

import android.content.Context;
import android.content.Intent;

/**
 * Created by HyFun on 2019/10/21.
 * Email: 775183940@qq.com
 * Description:
 */
public class SDR_HIKVISION_ISC {
    private SDR_HIKVISION_ISC() {
    }

    private static SDR_HIKVISION_ISC sdr_hikvision_isc;

    public static final SDR_HIKVISION_ISC getInstance() {
        if (sdr_hikvision_isc == null) {
            synchronized (SDR_HIKVISION_ISC.class) {
                if (sdr_hikvision_isc == null) {
                    sdr_hikvision_isc = new SDR_HIKVISION_ISC();
                }
            }
        }
        return sdr_hikvision_isc;
    }


    public void start(Context context, String serviceUrl, String authorization) {
        this.serviceUrl = serviceUrl;
        this.authorization = authorization;
        context.startActivity(new Intent(context, HikvisionMainActivity.class));
    }


    // ——————————————————————————SET————————————————————————————————————————————
    private boolean isControl = true;

    public boolean isControl() {
        return isControl;
    }

    public void setControl(boolean control) {
        isControl = control;
    }
    // ——————————————————————————GET————————————————————————————————————————————

    private String serviceUrl;

    public String getServiceUrl() {
        return serviceUrl;
    }

    private String authorization;

    public String getAuthorization() {
        return authorization;
    }
}
