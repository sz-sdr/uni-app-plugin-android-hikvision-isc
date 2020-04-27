package com.uniapp.plugin.hikvisionisc;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uniapp.library.baselib.ui.tree.TreeNode;
import com.uniapp.library.baselib.ui.tree.TreeNodeRecyclerAdapter;
import com.uniapp.library.baselib.util.AlertUtil;
import com.uniapp.plugin.hikvisionisc.base.HikvisionIscBaseActivity;
import com.uniapp.plugin.hikvisionisc.entity.Cameras;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HyFun on 2019/10/21.
 * Email: 775183940@qq.com
 * Description:
 */
class HikvisionRecyclerAdapter extends BaseQuickAdapter<HKItemControl, BaseViewHolder> implements Interface.HikIscPlayCallback {
    private HikvisionIscBaseActivity activity;

    public HikvisionRecyclerAdapter(int layoutResId, @Nullable List<HKItemControl> data, HikvisionIscBaseActivity activity) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HKItemControl item) {
        final int position = helper.getLayoutPosition();

        final FrameLayout frameLayout = helper.getView(R.id.hk_video_main_item_sfl_container);
        final TextureView textureView = helper.getView(R.id.hk_video_main_item_text);
        final ImageView imageView = helper.getView(R.id.hk_video_main_item_iv_add);
        textureView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);

// 点击事件
        {
            textureView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lastPosition != -1) {
                        // 设置之前的view为透明
                        FrameLayout lastFrameLayout = (FrameLayout) getViewByPosition(lastPosition, R.id.hk_video_main_item_sfl_container);
                        lastFrameLayout.setBackgroundColor(Color.TRANSPARENT);
                    }
                    // 设置当前的view为
                    frameLayout.setBackgroundColor(mContext.getResources().getColor(R.color.hikvision_color_primary));
                    lastPosition = position;
                }
            });

            textureView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new MaterialDialog.Builder(mContext)
                            .title("提示")
                            .content("是否关闭播放")
                            .positiveText("关闭")
                            .negativeText("取消")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    item.stopPreview();
                                }
                            })
                            .show();
                    return true;
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 显示选择的dialog
                    new Util.HKVideoPlayListDialog(mContext, treeNodeList, new TreeNodeRecyclerAdapter.OnTreeNodeSigleClickListener() {
                        @Override
                        public void onSigleClick(TreeNode treeNode, int visablePositon, int realDatasPositon, boolean isLeaf) {
                            // 开始加载播放
                            if (treeNode.getObject() instanceof Cameras) {
                                Cameras camera = (Cameras) treeNode.getObject();
                                textureView.setVisibility(View.VISIBLE);
                                imageView.setVisibility(View.GONE);
                                item.startPreview(camera.getCameraIndexCode(), textureView);
                            }
                        }
                    }).show();
                }
            });
        }

    }

    // ————————————————————————————————————GET和SET——————————————————————————————————————————————————
    private List<TreeNode> treeNodeList = new ArrayList<>();  // 摄像头树形列表

    public void setTreeNodeList(List<TreeNode> treeNodeList) {
        this.treeNodeList = treeNodeList;
    }

    private int lastPosition = -1;  // 最后一次点击的点

    public int getLastPosition() {
        return lastPosition;
    }

    // ————————————————————————————————————————————————————————————————————————————————————————————
    public void onResume() {
        List<HKItemControl> data = getData();
        for (HKItemControl item : data) {
            if (item.getTextureView() != null && item.getTextureView().isAvailable()) {
                item.onSurfaceTextureAvailable(item.getTextureView().getSurfaceTexture(), item.getTextureView().getWidth(), item.getTextureView().getHeight());
            }
        }
    }

    public void onPause() {
        List<HKItemControl> data = getData();
        for (HKItemControl item : data) {
            if (item.getTextureView() != null && item.getTextureView().isAvailable()) {
                item.onSurfaceTextureDestroyed(item.getTextureView().getSurfaceTexture());
            }
        }
    }

    // ————————————————————————————————————播放回调——————————————————————————————————————————————————
    @Override
    public void onLoading(int position, String message) {
        activity.showLoadingDialog(message);
    }

    @Override
    public void onHideLoading(int position) {
        activity.hideLoadingDialog();
    }

    @Override
    public void onPlaySuccess(int position, String message) {
        final FrameLayout frameLayout = (FrameLayout) getViewByPosition(position, R.id.hk_video_main_item_sfl_container);
        final TextureView textureView = (TextureView) getViewByPosition(position, R.id.hk_video_main_item_text);
        final ImageView imageView = (ImageView) getViewByPosition(position, R.id.hk_video_main_item_iv_add);

        onHideLoading(position);

    }

    @Override
    public void onPlayFailed(int position, String message) {
        final FrameLayout frameLayout = (FrameLayout) getViewByPosition(position, R.id.hk_video_main_item_sfl_container);
        final TextureView textureView = (TextureView) getViewByPosition(position, R.id.hk_video_main_item_text);
        final ImageView imageView = (ImageView) getViewByPosition(position, R.id.hk_video_main_item_iv_add);

        textureView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);

        onHideLoading(position);
        AlertUtil.showNegativeToastTop("第" + (position + 1) + "个位置", message);
    }

    @Override
    public void onStopSuccess(int position, String message) {
        final FrameLayout frameLayout = (FrameLayout) getViewByPosition(position, R.id.hk_video_main_item_sfl_container);
        final TextureView textureView = (TextureView) getViewByPosition(position, R.id.hk_video_main_item_text);
        final ImageView imageView = (ImageView) getViewByPosition(position, R.id.hk_video_main_item_iv_add);

        textureView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopFailed(int position, String message) {

    }

    @Override
    public void onCaptureSuccess(int position, String path) {

    }

    @Override
    public void onRecordStart(int position) {

    }

    @Override
    public void onRecordEnd(int position, String path) {

    }

    @Override
    public void onEnableVoice(int position, boolean enable) {

    }

    @Override
    public void onControlSuccess(int position, String message) {

    }

    @Override
    public void onControlFailed(int position, String message) {

    }
}
