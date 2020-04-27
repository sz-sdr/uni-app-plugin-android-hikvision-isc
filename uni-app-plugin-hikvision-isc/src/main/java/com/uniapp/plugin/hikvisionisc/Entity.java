package com.uniapp.plugin.hikvisionisc;

/**
 * Created by HyFun on 2019/10/21.
 * Email: 775183940@qq.com
 * Description:
 */
class Entity {

    /**
     * 标化平台base data
     *
     * @param <T>
     */
    public static class BaseData<T> {
        public static final int SUCCESS = 0;

        private int code;
        private T data;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }


    /**
     * 海康平台base data
     *
     * @param <T>
     */
    public static class HIKBaseData<T> {
        public static final String SUCCESS = "0";


        private String code;
        private T data;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }


    /**
     * 资源列表 摄像头列表
     */
    public static class Resource {

    }


    /**
     * 实时播放
     */
    public static class Preview {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
