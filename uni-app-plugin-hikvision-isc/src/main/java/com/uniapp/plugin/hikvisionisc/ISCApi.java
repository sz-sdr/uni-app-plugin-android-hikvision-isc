package com.uniapp.plugin.hikvisionisc;

import com.uniapp.plugin.hikvisionisc.entity.RegionsAndCameras;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by HyFun on 2019/10/21.
 * Email: 775183940@qq.com
 * Description:
 */
interface ISCApi {
    /**
     * 获取摄像头列表
     *
     * @return
     */
    @GET("app/cameras")
    Observable<Entity.BaseData<RegionsAndCameras>> getResourceList();


    /**
     * ISC转发接口
     *
     * @param url
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("transpond")
    Observable<ResponseBody> transform(@Field("iscPath") String url, @Field("jsonBody") String json);

}
