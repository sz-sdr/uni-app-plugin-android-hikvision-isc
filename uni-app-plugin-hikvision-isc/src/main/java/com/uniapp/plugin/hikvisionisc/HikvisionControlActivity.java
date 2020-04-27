package com.uniapp.plugin.hikvisionisc;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.TextureView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uniapp.library.baselib.util.AlertUtil;
import com.uniapp.plugin.hikvisionisc.base.HikvisionIscBaseActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class HikvisionControlActivity extends HikvisionIscBaseActivity implements Interface.HikIscPlayCallback {


    public static final int RQ_OPEN_HKVIDEO_CONTROL_CODE = 213;
    private static final String CAMERAID = "CAMERAID";

    TextureView mSurfaceView;


    private View viewOperationView, viewControlView;
    private View viewTakePhoto, viewRecord, viewAudio, viewRemote;
    private CheckBox rbControlTakePhoto, rbControlRecord, rbControlAudio, rbControlRemote;
    private View viewBack;

    /**
     * 十二个 控制按钮
     */
    ImageView ivLeftTop;
    ImageView ivTop;
    ImageView ivRightTop;
    ImageView ivLeft;
    ImageView ivRight;
    ImageView ivLeftBottom;
    ImageView ivBottom;
    ImageView ivRightBottom;
    ImageView ivFar;
    ImageView ivNear;
    ImageView ivZoomIn;
    ImageView ivZoomOut;
    ImageView ivMiddle;


    private String cameraId;
    private HKItemControl mHKItemControl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hikvision_control);

        Intent intent = getIntent();
        cameraId = intent.getStringExtra(CAMERAID);
        if (cameraId == null) finish();
        initToolbar();
        initView();
        initListener();
    }

    private void initToolbar() {
        setTitle("预览视频");
        setDisplayHomeAsUpEnabled();

        mSurfaceView = findViewById(R.id.hk_video_control_surfaceview);
        viewOperationView = findViewById(R.id.hk_video_control_view_control_operation);
        viewControlView = findViewById(R.id.hk_video_control_view_control_view);


        viewTakePhoto = findViewById(R.id.hk_video_control_view_takephoto);
        viewRecord = findViewById(R.id.hk_video_control_view_record);
        viewAudio = findViewById(R.id.hk_video_control_view_audio);
        viewRemote = findViewById(R.id.hk_video_control_view_remote);
        rbControlTakePhoto = findViewById(R.id.hk_video_control_rb_takephoto);
        rbControlRecord = findViewById(R.id.hk_video_control_rb_record);
        rbControlAudio = findViewById(R.id.hk_video_control_rb_audio);
        rbControlRemote = findViewById(R.id.hk_video_control_rb_remote);


        ivLeftTop = findViewById(R.id.hk_video_control_iv_left_top);
        ivTop = findViewById(R.id.hk_video_control_iv_top);
        ivRightTop = findViewById(R.id.hk_video_control_iv_right_top);
        ivLeft = findViewById(R.id.hk_video_control_iv_left);
        ivRight = findViewById(R.id.hk_video_control_iv_right);
        ivLeftBottom = findViewById(R.id.hk_video_control_iv_left_bottom);
        ivBottom = findViewById(R.id.hk_video_control_iv_bottom);
        ivRightBottom = findViewById(R.id.hk_video_control_iv_right_bottom);
        ivFar = findViewById(R.id.hk_video_control_iv_far);
        ivNear = findViewById(R.id.hk_video_control_iv_near);
        ivZoomIn = findViewById(R.id.hk_video_control_iv_zoom_in);
        ivZoomOut = findViewById(R.id.hk_video_control_iv_zoom_out);
        ivMiddle = findViewById(R.id.hk_video_control_iv_middle);


        viewBack = findViewById(R.id.hk_video_control_view_back);
    }

    private void initView() {
        // 根据是否可控制动态显示按钮
        boolean isControl = SDR_HIKVISION_ISC.getInstance().isControl();
        if (isControl) {
            viewTakePhoto.setVisibility(View.VISIBLE);
            viewRecord.setVisibility(View.VISIBLE);
            viewAudio.setVisibility(View.VISIBLE);
            viewRemote.setVisibility(View.VISIBLE);
        } else {
            viewTakePhoto.setVisibility(View.GONE);
            viewRecord.setVisibility(View.GONE);
            viewAudio.setVisibility(View.GONE);
            viewRemote.setVisibility(View.GONE);
        }


        mHKItemControl = new HKItemControl(0, this);
        mHKItemControl.startPreview(cameraId, mSurfaceView);
    }

    private void initListener() {
        viewTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RxPermissions(getActivity())
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    mHKItemControl.capture();
                                }
                            }
                        });
            }
        });

        viewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RxPermissions(getActivity())
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    if (mHKItemControl.isRecording()) {
                                        // 就停止
                                        mHKItemControl.captureRecordEnd();
                                    } else {
                                        // 开启
                                        mHKItemControl.captureRecordStart();
                                    }
                                }
                            }
                        });
            }
        });

        viewAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHKItemControl.isAudioing()) {
                    // 如果正在播放，那就关闭
                    mHKItemControl.enableVoice(false);
                } else {
                    // 如果没有打开，那就开启
                    mHKItemControl.enableVoice(true);
                }
            }
        });

        viewRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOperationView.setVisibility(View.GONE);
                viewControlView.setVisibility(View.VISIBLE);
            }
        });


        ivLeftTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 0, Interface.Control.LEFT_UP);
            }
        });

        ivTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 0, Interface.Control.UP);
            }
        });

        ivRightTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 0, Interface.Control.RIGHT_UP);
            }
        });

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 0, Interface.Control.LEFT);
            }
        });

        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 0, Interface.Control.RIGHT);
            }
        });

        ivLeftBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 0, Interface.Control.LEFT_DOWN);
            }
        });

        ivBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 0, Interface.Control.DOWN);
            }
        });

        ivRightBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 0, Interface.Control.RIGHT_DOWN);
            }
        });

        ivFar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 0, Interface.Control.FOCUS_FAR);
            }
        });

        ivNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 0, Interface.Control.FOCUS_NEAR);
            }
        });

        ivZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 0, Interface.Control.ZOOM_IN);
            }
        });

        ivZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 0, Interface.Control.ZOOM_OUT);
            }
        });

        ivMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHKItemControl.control(cameraId, 1, mHKItemControl.getLastCommand());
            }
        });

        viewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOperationView.setVisibility(View.VISIBLE);
                viewControlView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void setNavigationOnClickListener() {
        new MaterialDialog.Builder(getContext())
                .title("提示")
                .content("退出预览视频？")
                .positiveText("退出")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Observable.just(0)
                                .flatMap(new Function<Integer, ObservableSource<Boolean>>() {
                                    @Override
                                    public ObservableSource<Boolean> apply(Integer integer) throws Exception {
                                        mHKItemControl.stopPreview();
                                        return Util.RxUtils.createData(true);
                                    }
                                }).compose(Util.RxUtils.io_main())
                                .subscribe(new Consumer<Object>() {
                                    @Override
                                    public void accept(Object object) throws Exception {
                                        finish();
                                    }
                                });
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        setNavigationOnClickListener();
    }

    // 系统

    @Override
    protected void onResume() {
        super.onResume();
        if (mSurfaceView.isAvailable()) {
            mHKItemControl.onSurfaceTextureAvailable(mSurfaceView.getSurfaceTexture(), mSurfaceView.getWidth(), mSurfaceView.getHeight());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSurfaceView.isAvailable()) {
            mHKItemControl.onSurfaceTextureDestroyed(mSurfaceView.getSurfaceTexture());
        }
    }

    // ——————————————————————————播放回调——————————————————————————————————

    @Override
    public void onLoading(int position, String message) {
        showLoadingDialog(message);
    }

    @Override
    public void onHideLoading(int position) {
        hideLoadingDialog();
    }

    @Override
    public void onPlaySuccess(int position, String message) {
        hideLoadingDialog();
    }

    @Override
    public void onPlayFailed(int position, String message) {
        hideLoadingDialog();
        AlertUtil.showNegativeToastTop(message,"");
    }

    @Override
    public void onStopSuccess(int position, String message) {

    }

    @Override
    public void onStopFailed(int position, String message) {

    }

    @Override
    public void onCaptureSuccess(int position, String path) {
        AlertUtil.showPositiveToastTop("保存成功","图片保存至：" + path);
    }

    @Override
    public void onRecordStart(int position) {
        AlertUtil.showPositiveToastTop("开始录像","");
        rbControlRecord.setChecked(true);
    }

    @Override
    public void onRecordEnd(int position, String path) {
        AlertUtil.showPositiveToastTop("录像成功","视频保存至：" + path);
        rbControlRecord.setChecked(false);
    }

    @Override
    public void onEnableVoice(int position, boolean enable) {
        if (enable) {
            AlertUtil.showPositiveToastTop("已开启","");
        } else {
            AlertUtil.showPositiveToastTop("已关闭","");
        }
        rbControlAudio.setChecked(enable);
    }

    @Override
    public void onControlSuccess(int position, String message) {
        hideLoadingDialog();
        AlertUtil.showPositiveToastTop(message,"");
    }

    @Override
    public void onControlFailed(int position, String message) {
        hideLoadingDialog();
        AlertUtil.showNegativeToastTop(message,"");
    }


    /**
     * 开启此activity
     *
     * @param activity
     * @param cameraId
     */
    public static void startHKVideoControlActivity(Activity activity, String cameraId) {
        Intent intent = new Intent(activity, HikvisionControlActivity.class);
        intent.putExtra(CAMERAID, cameraId);
        activity.startActivityForResult(intent, RQ_OPEN_HKVIDEO_CONTROL_CODE);
    }
}
