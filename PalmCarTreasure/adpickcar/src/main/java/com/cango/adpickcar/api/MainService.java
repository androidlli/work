package com.cango.adpickcar.api;

import com.cango.adpickcar.model.BaseData;
import com.cango.adpickcar.model.CarTakeTaskList;
import com.cango.adpickcar.model.ServerTime;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by cango on 2017/10/9.
 */

public interface MainService {
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

    @POST("api/user/loginout")
    Observable<BaseData> logout(@Body Map<String, Object> requestContent);

    @GET("api/cartake/getcartaketasklist")
    Observable<CarTakeTaskList> getDatasByStatus(@Query("UserID") String UserID, @Query("CustName") String CustName,
                                                 @Query("LicensePlateNO") String LicensePlateNO,
                                                 @Query("CarBrandName") String CarBrandName,
                                                 @Query("QueryType") String QueryType,
                                                 @Query("PageIndex") String PageIndex,
                                                 @Query("PageSize") String PageSize);

    //确认接车
    @POST("api/cartake/cartakestoreconfirm")
    Observable<BaseData> carTakeStoreConfirm(@Body Map<String,Object> requestContent);
}
