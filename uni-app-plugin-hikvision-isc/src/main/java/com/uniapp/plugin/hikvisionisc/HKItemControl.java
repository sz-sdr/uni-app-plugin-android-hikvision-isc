package com.uniapp.plugin.hikvisionisc;

import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.TextureView;

import com.google.gson.reflect.TypeToken;
import com.hikvision.open.hikvideoplayer.HikVideoPlayer;
import com.hikvision.open.hikvideoplayer.HikVideoPlayerCallback;
import com.hikvision.open.hikvideoplayer.HikVideoPlayerFactory;
import com.orhanobut.logger.Logger;
import com.uniapp.library.baselib.http.HttpClient;

import java.text.MessageFormat;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by HyFun on 2019/10/21.
 * Email: 775183940@qq.com
 * Description:
 */
class HKItemControl implements TextureView.SurfaceTextureListener, HikVideoPlayerCallback {

    private int position;   // 播放的位置
    private Interface.HikIscPlayCallback hikIscPlayCallback;

    public HKItemControl(int position, Interface.HikIscPlayCallback hikIscPlayCallback) {
        this.position = position;
        this.hikIscPlayCallback = hikIscPlayCallback;
        mPlayer = HikVideoPlayerFactory.provideHikVideoPlayer();
    }

    private Interface.PlayerStatus mPlayerStatus = Interface.PlayerStatus.IDLE;//默认闲置
    private HikVideoPlayer mPlayer;  // 播放器


    // ———————————————————————————————————————公共方法—————————————————————————————————————

    /**
     * 开始实时预览
     *
     * @param textureView
     */
    public void startPreview(String cameraIndexCode, final TextureView textureView) {
        if (mPlayerStatus == Interface.PlayerStatus.SUCCESS) {
            return;
        }
        mPlayerStatus = Interface.PlayerStatus.LOADING;
        cameraIndex = cameraIndexCode;
        this.textureView = textureView;
        this.textureView.setSurfaceTextureListener(this);
        // 开始获取播放url
        final EntityRequest.PreviewRequest previewRequest = new EntityRequest.PreviewRequest();
        previewRequest.setCameraIndexCode(cameraIndexCode);

        hikIscPlayCallback.onLoading(position, "正在加载播放...");
        HttpService.getService().transform("/api/video/v1/cameras/previewURLs", HttpClient.gson.toJson(previewRequest))
                .flatMap(new Function<ResponseBody, ObservableSource<Entity.HIKBaseData<Entity.Preview>>>() {
                    @Override
                    public ObservableSource<Entity.HIKBaseData<Entity.Preview>> apply(ResponseBody responseBody) throws Exception {
                        String json = responseBody.string();
                        Entity.HIKBaseData<Entity.Preview> previewHIKBaseData = HttpClient.gson.fromJson(json, new TypeToken<Entity.HIKBaseData<Entity.Preview>>() {
                        }.getType());
                        return Util.RxUtils.createData(previewHIKBaseData);
                    }
                })
                .compose(Util.RxUtils.<Entity.Preview>baseHKData())
                .flatMap(new Function<Entity.Preview, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Entity.Preview preview) throws Exception {
                        mPlayer.setSurfaceTexture(textureView.getSurfaceTexture());
                        return Util.RxUtils.createData(mPlayer.startRealPlay(preview.getUrl(), HKItemControl.this));
                    }
                })
                .compose(Util.RxUtils.<Boolean>io_main())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                            // 说明播放失败
                            onPlayerStatus(Status.FAILED, mPlayer.getLastError());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        hikIscPlayCallback.onPlayFailed(position, throwable.getMessage());
                        Logger.e(throwable, throwable.getMessage());
                    }
                });
    }


    /**
     * 正常关闭
     */
    public void stopPreview() {
        if (mPlayerStatus == Interface.PlayerStatus.SUCCESS) {
            if (isRecording) {
                // 如果正在录像，需要先关闭录像
                hikIscPlayCallback.onStopFailed(position, "请先关闭录像");
                return;
            }

            try {
                mPlayer.stopPlay();
                mPlayerStatus = Interface.PlayerStatus.IDLE;//释放这个窗口
                hikIscPlayCallback.onStopSuccess(position, "停止成功");
            } catch (Exception e) {
                // 停止播放失败
                hikIscPlayCallback.onStopFailed(position, e.getMessage());
            }
        }
    }

    /**
     * 抓取图片
     */
    public void capture() {
        if (mPlayerStatus != Interface.PlayerStatus.SUCCESS) {
            return;
        }

        //抓图
        String path = Util.getCaptureImagePath(textureView.getContext());
        if (mPlayer.capturePicture(path)) {
            hikIscPlayCallback.onCaptureSuccess(position, path);
            Util.notifyPhotoChanged(textureView.getContext(), path);
        }
    }

    private String recordPath; // 录像存储路径

    /**
     * 开始录制视频
     */
    public void captureRecordStart() {
        if (mPlayerStatus != Interface.PlayerStatus.SUCCESS) {
            return;
        }
        //开始录像
        recordPath = Util.getLocalRecordPath(textureView.getContext());
        if (mPlayer.startRecord(recordPath)) {
            isRecording = true;
            hikIscPlayCallback.onRecordStart(position);
        }
    }


    /**
     * 停止录制视频
     */
    public void captureRecordEnd() {
        //关闭录像
        mPlayer.stopRecord();
        isRecording = false;
        hikIscPlayCallback.onRecordEnd(position, recordPath);
        Util.notifyPhotoChanged(textureView.getContext(), recordPath);
    }


    /**
     * 开启关闭声音
     */
    public void enableVoice(boolean enable) {
        if (mPlayerStatus != Interface.PlayerStatus.SUCCESS) {
            return;
        }
        if (enable) {
            //打开声音
            if (mPlayer.enableSound(true)) {
                isAudioing = true;
                hikIscPlayCallback.onEnableVoice(position, true);
            }
        } else {
            //关闭声音
            if (mPlayer.enableSound(false)) {
                isAudioing = false;
                hikIscPlayCallback.onEnableVoice(position, false);
            }
        }
    }


    /**
     * 云台操作
     *
     * @param cameraIndexCode
     * @param action
     * @param command
     */

    public void control(String cameraIndexCode, int action, final String command) {
        hikIscPlayCallback.onLoading(position, "正在启动控制...");
        // 正在播放的时候
        if (mPlayerStatus != Interface.PlayerStatus.SUCCESS) {
            hikIscPlayCallback.onControlFailed(position, "播放的时候才能操作");
            return;
        }
        if (TextUtils.isEmpty(command)) {
            hikIscPlayCallback.onControlFailed(position, "没有任何操作");
            return;
        }

        EntityRequest.CloudControl control = new EntityRequest.CloudControl(cameraIndexCode, action, command);
        HttpService.getService()
                .transform("/api/video/v1/ptzs/controlling", HttpClient.gson.toJson(control))
                .flatMap(new Function<ResponseBody, ObservableSource<Entity.HIKBaseData<String>>>() {
                    @Override
                    public ObservableSource<Entity.HIKBaseData<String>> apply(ResponseBody responseBody) throws Exception {
                        String json = responseBody.string();
                        Entity.HIKBaseData<String> stringHIKBaseData = HttpClient.gson.fromJson(json, new TypeToken<Entity.HIKBaseData<String>>() {
                        }.getType());
                        return Util.RxUtils.createData(stringHIKBaseData);
                    }
                })
                .compose(Util.RxUtils.<String>baseHKBoolean())
                .compose(Util.RxUtils.<Boolean>io_main())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        // 说明执行成功
                        lastCommand = command;
                        hikIscPlayCallback.onControlSuccess(position, "执行指令成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        hikIscPlayCallback.onControlFailed(position, throwable.getMessage());
                        Logger.e(throwable, throwable.getMessage());
                    }
                });


    }


    // —————————————————————————————————————get 和 set———————————————————————————————————————————

    private TextureView textureView;  // 播放的界面

    private String lastCommand; // 云台操作最后一次的操作动作

    private String cameraIndex;

    private boolean isRecording = false;  // 是否正在录像
    private boolean isAudioing = false;  // 是否正在播放声音

    /**
     * 获取播放控件
     *
     * @return
     */
    public TextureView getTextureView() {
        return textureView;
    }

    /**
     * 获取当前所在的位置
     *
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * 获取当前播放的状态
     *
     * @return
     */
    public Interface.PlayerStatus getStatus() {
        return mPlayerStatus;
    }

    /**
     * 获取最后一次的指令
     *
     * @return
     */
    public String getLastCommand() {
        return lastCommand;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public boolean isAudioing() {
        return isAudioing;
    }

    // —————————————————————————————————————TextureView的声明周期———————————————————————————————————————————

    /**
     * 播放结果回调
     *
     * @param status    共四种状态：SUCCESS（播放成功）、FAILED（播放失败）、EXCEPTION（取流异常）、FINISH（回放结束）
     * @param errorCode 错误码，只有 FAILED 和 EXCEPTION 才有值
     */
    @Override
    public void onPlayerStatus(@NonNull final Status status, final int errorCode) {
        // 注意: 由于 HikVideoPlayerCallback 是在子线程中进行回调的，所以一定要切换到主线程处理UI
        Observable.just(0)
                .compose(Util.RxUtils.<Integer>io_main())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.e("播放结果:::::" + status + ">>>>>>>>>" + "错误码::::::::" + errorCode);
                        switch (status) {
                            case SUCCESS:
                                //播放成功
                                mPlayerStatus = Interface.PlayerStatus.SUCCESS;
                                hikIscPlayCallback.onPlaySuccess(position, "播放成功");
                                textureView.setKeepScreenOn(true);//保持亮屏
                                break;
                            case FAILED:
                                //播放失败
                                mPlayerStatus = Interface.PlayerStatus.FAILED;
                                hikIscPlayCallback.onPlayFailed(position, MessageFormat.format("预览失败，错误码：{0}", Integer.toHexString(errorCode)));
                                break;
                            case EXCEPTION:
                                //取流异常 停止播放
                                mPlayerStatus = Interface.PlayerStatus.EXCEPTION;
                                // 注意:异常时关闭取流
                                mPlayer.stopPlay();
                                hikIscPlayCallback.onPlayFailed(position, MessageFormat.format("取流发生异常，错误码：{0}", Integer.toHexString(errorCode)));
                                break;
                        }
                    }
                });
    }


    // —————————————————————————————————————TextureView的声明周期———————————————————————————————————————————

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (mPlayerStatus == Interface.PlayerStatus.STOPPING) {
            //恢复处于暂停播放状态的窗口
            startPreview(cameraIndex, textureView);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mPlayerStatus == Interface.PlayerStatus.SUCCESS) {
            mPlayerStatus = Interface.PlayerStatus.STOPPING;//暂停播放，再次进入时恢复播放
            mPlayer.stopPlay();
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

}
