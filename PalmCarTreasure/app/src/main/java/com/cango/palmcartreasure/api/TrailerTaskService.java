package com.cango.palmcartreasure.api;

import com.cango.palmcartreasure.model.CallRecord;
import com.cango.palmcartreasure.model.CaseInfo;
import com.cango.palmcartreasure.model.CheckOrderData;
import com.cango.palmcartreasure.model.CustomerInfo;
import com.cango.palmcartreasure.model.HomeVisitRecord;
import com.cango.palmcartreasure.model.NavigationCar;
import com.cango.palmcartreasure.model.TaskAbandon;
import com.cango.palmcartreasure.model.TaskDetailData;
import com.cango.palmcartreasure.model.TrailerInfo;
import com.cango.palmcartreasure.model.TypeTaskData;
import com.cango.palmcartreasure.model.WareHouse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by cango on 2017/4/24.
 */

public interface TrailerTaskService {
    String BASE_URL = Api.BASE_URL;

    @GET("trailer/newtasklist")
    Observable<TypeTaskData> getNewTaskList(@Query("userid") int userId, @Query("LAT") double lat, @Query("LON") double lon,
                                            @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @GET("trailer/taskinprogresslist")
    Observable<TypeTaskData> getTaskInprogressList(@Query("userid") int userId, @Query("LAT") double lat, @Query("LON") double lon,
                                                   @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @GET("trailer/taskdonelist")
    Observable<TypeTaskData> getTaskDoneList(@Query("userid") int userId, @Query("LAT") double lat, @Query("LON") double lon,
                                             @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    //GET /trailer/taskquery?userid={userid}&applyCD={applyCD}&customerName={customerName}&pageIndex={pageIndex}&pageSize={pageSize}&ApiToken={APITOKEN}
    //时间标识（0：当月；1：半年；2：一年）
    @GET("trailer/taskquery")
    Observable<TypeTaskData> taskQuery(@Query("userid") int userId, @Query("applyCD") String applyCD, @Query("customerName") String customerName,
                                       @Query("timeFlag") int timeFlag, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    //POST /trailer/starttasksubmit?ApiToken={APITOKEN}  开始任务
    @POST("trailer/starttasksubmit")
    Observable<TaskAbandon>  startTaskSubmit(@Body Map<String,Object> objectMap);

    //POST /trailer/checkpiontsubmit?ApiToken={APITOKEN}
    @Multipart
    @POST("trailer/checkpiontsubmit")
    Observable<TaskAbandon> checkPiontSubmit(@Part("userid") RequestBody userId,@Part("LAT") RequestBody lat,@Part("LON") RequestBody lon,
                                             @Part("agencyID") RequestBody agencyID,@Part("caseID") RequestBody caseID,
                                             @Part("notifyCustImm") RequestBody notifyCustImm,@Part("answerList") RequestBody answerList,
                                             @Part("realSPID") RequestBody realSPID,@Part("tmpReason") RequestBody tmpReason,
                                             @Part MultipartBody.Part photo,@Part("datasource") RequestBody datasource);

    //POST /trailer/checkpiontsubmit?ApiToken={APITOKEN}
    @Multipart
    @POST("trailer/checkpiontsubmit")
    Observable<TaskAbandon> checkPiontSubmitNoFile(@Part("userid") RequestBody userId,@Part("LAT") RequestBody lat,@Part("LON") RequestBody lon,
                                             @Part("agencyID") RequestBody agencyID,@Part("caseID") RequestBody caseID,
                                             @Part("notifyCustImm") RequestBody notifyCustImm,@Part("answerList") RequestBody answerList,
                                             @Part("realSPID") RequestBody realSPID,@Part("tmpReason") RequestBody tmpReason,@Part("datasource") RequestBody datasource);

    //POST /trailer/godownsubmit?ApiToken={APITOKEN} 送车入库
//    @Multipart
//    @POST("trailer/godownsubmit")
//    Observable<TaskAbandon> godownSubmit(@Part("userid") RequestBody userId, @Part("LAT") RequestBody lat, @Part("LON") RequestBody lon,
//                                         @Part("agencyID") RequestBody agencyID, @Part("caseID") RequestBody caseID, @Part("realSPID") RequestBody realSPID,
//                                         @Part("answerList") RequestBody answerList,@PartMap Map<String,RequestBody> requestBodyMap);
    @Multipart
    @POST("trailer/godownsubmit")
    Observable<TaskAbandon> godownSubmit(@Part("userid") RequestBody userId, @Part("LAT") RequestBody lat, @Part("LON") RequestBody lon,
                                         @Part("agencyID") RequestBody agencyID, @Part("caseID") RequestBody caseID,
                                         @PartMap Map<String,RequestBody> requestBodyMap,@Part("datasource") RequestBody datasource);

//    @POST("trailer/godownsubmit")
//    Observable<TaskAbandon> godownSubmit(@Body RequestBody requestBody);

    //GET /trailer/callrecord?userId={userId}&agencyID={agencyID}&caseID={caseID}&ApiToken={APITOKEN} 获取电催记录
    @GET("trailer/callrecord")
    Observable<CallRecord> callRecord(@Query("userid") int userId, @Query("agencyID") int agencyID, @Query("caseID") int caseID,@Query("datasource") int datasource);

    ///trailer/homevisitrecord?userid={userid}&agencyID={agencyID}&caseID={caseID}&ApiToken={APITOKEN} 获取家访记录
    @GET("trailer/homevisitrecord")
    Observable<HomeVisitRecord> homeVisitRecord(@Query("userid") int userId, @Query("agencyID") int agencyID, @Query("caseID") int caseID,@Query("datasource") int datasource);

    //GET /trailer/trailerinfo?userid={userid}&applyID={applyID}&applyCD={applyCD}&caseID={caseID}&ApiToken={APITOKEN} 获取拖车信息
    @GET("trailer/trailerinfo")
    Observable<TrailerInfo> trailerInfo(@Query("userid") int userId, @Query("applyID") int applyID, @Query("applyCD") String applyCD,@Query("caseID") int caseID,@Query("datasource") int datasource);

    //GET /trailer/caseinfo?userid={userid}&agencyID={agencyID}&caseID={caseID}&ApiToken={APITOKEN} 获取案件信息
    @GET("trailer/caseinfo")
    Observable<CaseInfo> caseInfo(@Query("userid") int userId, @Query("agencyID") int agencyID, @Query("caseID") int caseID,@Query("datasource") int datasource);

    //GET /trailer/customerinfo?userid={userid}&agencyID={agencyID}&caseID={caseID}&ApiToken={APITOKEN} 获取客户信息
    @GET("trailer/customerinfo")
    Observable<CustomerInfo> customerinfo(@Query("userid") int userId, @Query("agencyID") int agencyID, @Query("caseID") int caseID,@Query("datasource") int datasource);

    //GET /trailer/navigation2car?userId={userId}&agencyID={agencyID}&caseID={caseID}&ApiToken={APITOKEN} 任务开始导航
    @GET("trailer/navigation2car")
    Observable<NavigationCar> navigationCar(@Query("userid") int userId, @Query("agencyID") int agencyID, @Query("caseID") int caseID,@Query("startTime") String startTime,@Query("datasource") int datasource);

    //GET /trailer/navigation2warehouse?userId={userId}&agencyID={agencyID}&caseID={caseID}&LAT={LAT}&LON={LON}&ApiToken={APITOKEN} 送车入库导航
    @GET("trailer/navigation2warehouse")
    Observable<WareHouse> wareHouse(@Query("userid") int userId, @Query("agencyID") int agencyID, @Query("caseID") int caseID,
                                    @Query("LAT") double lat, @Query("LON") double lon,@Query("province") String province,@Query("datasource") int datasource);

    //GET /trailer/docdownload?userid={userid}&agencyID={agencyID}&caseID={caseID}&docType={docType}&ApiToken={APITOKEN} 获取下载文件
    @GET("trailer/docdownload")
    Call<ResponseBody> docDownLoad(@Query("userid") int userId, @Query("agencyID") int agencyID, @Query("caseID") int caseID,
                                   @Query("docType") String docType,@Query("datasource") int datasource);

    //POST /trailer/agencysave?ApiToken={APITOKEN} 增加拖车注记
    @POST("trailer/agencysave")
    Observable<TaskAbandon> agencySave(@Body Map<String,Object> objectMap);

//    GET /trailer/checkquestion?userid={userid}&agencyID={agencyID}&applyCD={applyCD}&caseID={caseID}&ApiToken={APITOKEN} 拖车信息校验接口
    @GET("trailer/checkquestion")
    Observable<CheckOrderData> getCheckOrderData(@Query("userid") int userId, @Query("agencyID") int agencyID, @Query("applyCD") String applyCD,
                                                 @Query("caseID") int caseID,@Query("datasource") int datasource);
}
