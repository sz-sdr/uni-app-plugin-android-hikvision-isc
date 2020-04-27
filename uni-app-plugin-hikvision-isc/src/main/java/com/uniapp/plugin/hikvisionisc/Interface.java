package com.uniapp.plugin.hikvisionisc;

/**
 * Created by HyFun on 2019/10/21.
 * Email: 775183940@qq.com
 * Description:
 */
interface Interface {
    /**
     * 播放视频的监听
     */
    public interface HikIscPlayCallback {
        void onLoading(int position, String message);

        void onHideLoading(int position);

        void onPlaySuccess(int position, String message);

        void onPlayFailed(int position, String message);

        void onStopSuccess(int position, String message);

        void onStopFailed(int position, String message);

        void onCaptureSuccess(int position, String path);

        void onRecordStart(int position);

        void onRecordEnd(int position, String path);

        void onEnableVoice(int position, boolean enable);

        void onControlSuccess(int position, String message);

        void onControlFailed(int position, String message);
    }


    /**
     * 播放状态
     */
    public enum PlayerStatus {
        IDLE,//闲置状态
        LOADING,//加载中状态
        SUCCESS,//播放成功
        STOPPING,//暂时停止播放
        FAILED,//播放失败
        EXCEPTION,//播放过程中出现异常
        FINISH//回放结束
    }

    /**
     * 云台控制
     */
    public interface Control {
        String LEFT = "LEFT";
        String RIGHT = "RIGHT";
        String UP = "UP";
        String DOWN = "DOWN";
        String ZOOM_IN = "ZOOM_IN";
        String ZOOM_OUT = "ZOOM_OUT";
        String LEFT_UP = "LEFT_UP";
        String LEFT_DOWN = "LEFT_DOWN";
        String RIGHT_UP = "RIGHT_UP";
        String RIGHT_DOWN = "RIGHT_DOWN";
        String FOCUS_NEAR = "FOCUS_NEAR";
        String FOCUS_FAR = "FOCUS_FAR";
        String IRIS_ENLARGE = "IRIS_ENLARGE";
        String IRIS_REDUCE = "IRIS_REDUCE";
    }


}
