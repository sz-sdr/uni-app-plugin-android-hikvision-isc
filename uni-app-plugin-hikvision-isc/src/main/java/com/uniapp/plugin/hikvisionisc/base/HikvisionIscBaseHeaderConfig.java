package com.uniapp.plugin.hikvisionisc.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

import com.uniapp.library.baselib.base.HeaderBarDefaultConfig;
import com.uniapp.plugin.hikvisionisc.R;

/**
 * Created by HyFun on 2020/3/31.
 * Email: 775183940@qq.com
 * Description:
 */
public class HikvisionIscBaseHeaderConfig implements HeaderBarDefaultConfig {

    private Context context;

    public HikvisionIscBaseHeaderConfig(Context context) {
        this.context = context;
    }

    @Override
    public int onHeaderBarToolbarRes() {
        return R.layout.hikvision_isc_layout_public_toolbar_white;
    }

    @Override
    public int onHeaderBarTextGravity() {
        return Gravity.LEFT;
    }

    @Override
    public Drawable onHeaderBarDrawable() {
        return new ColorDrawable(context.getResources().getColor(R.color.hikvision_color_primary));
    }

    @Override
    public int onActivityOrientation() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }
}
