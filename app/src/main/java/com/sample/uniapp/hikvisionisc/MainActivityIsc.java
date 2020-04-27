package com.sample.uniapp.hikvisionisc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.uniapp.plugin.hikvisionisc.SDR_HIKVISION_ISC;

public class MainActivityIsc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickOpen(View view) {
        SDR_HIKVISION_ISC.getInstance().start(this, "http://10.215.17.158:9066/", "aaa");
    }
}
