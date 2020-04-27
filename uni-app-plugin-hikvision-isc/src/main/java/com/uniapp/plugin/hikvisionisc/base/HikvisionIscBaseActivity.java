package com.uniapp.plugin.hikvisionisc.base;

import com.uniapp.library.baselib.base.BaseActivity;
import com.uniapp.library.baselib.base.HeaderBarDefaultConfig;
import com.uniapp.library.baselib.ui.dialog.SdrLoadingDialog;

/**
 * Created by HyFun on 2020/3/31.
 * Email: 775183940@qq.com
 * Description:
 */
public class HikvisionIscBaseActivity extends BaseActivity {
    @Override
    public HeaderBarDefaultConfig onHeaderBarConfig() {
        return new HikvisionIscBaseHeaderConfig(getContext());
    }


    private SdrLoadingDialog loadingDialog;

    public void showLoadingDialog(String title) {
        if (loadingDialog == null) {
            loadingDialog = new SdrLoadingDialog(getActivity());
        }
        loadingDialog.setTitle(title);
        loadingDialog.show();
    }

    public void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
