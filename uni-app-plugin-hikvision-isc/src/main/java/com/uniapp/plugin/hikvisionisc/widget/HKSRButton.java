package com.uniapp.plugin.hikvisionisc.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/5/25.
 */

public class HKSRButton extends android.support.v7.widget.AppCompatRadioButton {
    public HKSRButton(Context context) {
        super(context);
    }

    public HKSRButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HKSRButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
