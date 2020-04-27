package com.uniapp.plugin.hikvisionisc;

/**
 * Created by HyFun on 2019/10/21.
 * Email: 775183940@qq.com
 * Description:
 */
class EntityRequest {
    /**
     * 获取预览url
     */
    public static class PreviewRequest {

        /**
         * cameraIndexCode : 748d84750e3a4a5bbad3cd4af9ed5101
         * streamType : 0
         * protocol : rtsp
         * transmode : 1
         */

        private String cameraIndexCode;
        private int streamType;
        private String protocol;
        private int transmode;

        public String getCameraIndexCode() {
            return cameraIndexCode;
        }

        public void setCameraIndexCode(String cameraIndexCode) {
            this.cameraIndexCode = cameraIndexCode;
        }

        public int getStreamType() {
            return streamType;
        }

        public void setStreamType(int streamType) {
            this.streamType = streamType;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public int getTransmode() {
            return transmode;
        }

        public void setTransmode(int transmode) {
            this.transmode = transmode;
        }
    }


    /**
     * 云台控制
     */
    public static class CloudControl {
        private String cameraIndexCode;
        private int action;  // 0 开始 1 停止
        private String command;

        public CloudControl(String cameraIndexCode, int action, String command) {
            this.cameraIndexCode = cameraIndexCode;
            this.action = action;
            this.command = command;
        }

        public String getCameraIndexCode() {
            return cameraIndexCode;
        }

        public void setCameraIndexCode(String cameraIndexCode) {
            this.cameraIndexCode = cameraIndexCode;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }
    }

}
