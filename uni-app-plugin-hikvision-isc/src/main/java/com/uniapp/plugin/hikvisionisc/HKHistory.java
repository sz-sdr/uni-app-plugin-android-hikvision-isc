package com.uniapp.plugin.hikvisionisc;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/27.
 */

class HKHistory implements Serializable {
    private int viewNum;
    private List<CameraInfo> mCameraInfoList;

    public HKHistory(int viewNum, List<CameraInfo> cameraInfoList) {
        this.viewNum = viewNum;
        mCameraInfoList = cameraInfoList;
    }

    public static class CameraInfo implements Serializable {
        private int position;
        private String cameraID;

        public CameraInfo(int position, String cameraID) {
            this.position = position;
            this.cameraID = cameraID;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getCameraID() {
            return cameraID;
        }

        public void setCameraID(String cameraID) {
            this.cameraID = cameraID;
        }
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public List<CameraInfo> getCameraInfoList() {
        return mCameraInfoList;
    }

    public void setCameraInfoList(List<CameraInfo> cameraInfoList) {
        mCameraInfoList = cameraInfoList;
    }
}
