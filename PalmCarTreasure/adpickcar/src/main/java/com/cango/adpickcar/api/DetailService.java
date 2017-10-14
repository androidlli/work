package com.cango.adpickcar.api;

import com.cango.adpickcar.model.BaseData;
import com.cango.adpickcar.model.BaseInfo;
import com.cango.adpickcar.model.CarFilesInfo;
import com.cango.adpickcar.model.CarInfo;
import com.cango.adpickcar.model.ServerTime;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by cango on 2017/10/12.
 */

public interface DetailService {
    /**
     * POST 方法数据统一加密传输。
     * 加密要求：
     * 1.登录方法 用每日变动密钥加密
     * 2.其它POST方法使用用户TOKEN加密
     * 3.加密请求JSON数据格式：
     * {
     * "RequestContent":"加密后的请求内容"
     * }
     */
    String BASE_URL = Api.BASE_URL;

    @GET("api/user/getservertime")
    Observable<ServerTime> getServerTime();

    //获取接车基本信息
    @GET("api/cartake/getcartakestorebaseinfo")
    Observable<BaseInfo> GetCarTakeStoreBaseInfo(@Query("CTSID") String CTSID, @Query("DisCarID") String DisCarID);

    //获取车辆信息
    @GET("api/cartake/getcartakestorecarinfo")
    Observable<CarInfo> GetCarTakeStoreCarInfo(@Query("DisCarID") String DisCarID);

    //保存基本信息和物品信息
    @POST("api/cartake/savecartakestorebaseinfo")
    Observable<BaseData> saveCarBasicItemInfo(@Body Map<String, Object> requestContent);

    //保存车辆信息
    @POST("api/cartake/savecarinfo")
    Observable<BaseData> saveCarInfo(@Body Map<String, Object> requestContent);

    //获取影像信息
    @GET("api/cartake/getcartakestorecarfiles")
    Observable<CarFilesInfo> getCarFilesInfo(@Query("DisCarID") String DisCarID);
}

