package com.watertransport.api;


import com.watertransport.BuildConfig;
import com.watertransport.entity.LoginResponse;

import cn.timeface.timekit.support.NetResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhangsheng on 2017/6/16.
 */

public interface ApiStores {
    /**
     * 根地址
     */
    String BASE_URL = BuildConfig.API_URL;

    /**
     * 手机基本信息注册
     */
    @POST("auth/firstRun?platform=2")
    Observable<NetResponse> firstRun(@Query("deviceName") String deviceName,
                                     @Query("osVersion") String osVersion,
                                     @Query("clientVersion") String clientVersion,
                                     @Query("screen") String screen);

    @GET("userAnnounce/list")
    Observable<NetResponse> userAnnounce(@Query("pageNo") String pageNo,
                                         @Query("pageSize") String pageSize);

    @GET("login")
    Observable<LoginResponse> login(@Query("loginName") String loginName,
                                    @Query("password") String password);

    @GET("userRegister")
    Observable<NetResponse> registerBoat(@Query("loginName") String loginName,
                                         @Query("password") String password,
                                         @Query("userType") int userType,
                                         @Query("mobile") String mobile,
                                         @Query("shipCode") String shipCode,
                                         @Query("name") String name,
                                         @Query("belongs") String belongs,
                                         @Query("belongsCompany") String belongsCompany,
                                         @Query("shipLicense") String shipLicense,
                                         @Query("tonnage") String tonnage);

    @GET("userRegister")
    Observable<NetResponse> registerCargo(@Query("loginName") String loginName,
                                          @Query("password") String password,
                                          @Query("userType") int userType,
                                          @Query("mobile") String mobile,
                                          @Query("name") String name,
                                          @Query("companyName") String companyName,
                                          @Query("addrees") String addrees);

    /*
    * http://59.110.141.52:8080/water_transport/app/supply/add?cargoId=20&cargoName=煤炭&transporterName=合肥长江海运科技有限公司
&loadTerminal=马鞍山&unloadTerminal=巢湖&tonnage=1000000&tonnageCost=20&statue=0&remarks=黄花树下不见不散&userId=25
    * */
    @GET("supply/add")
    Observable<NetResponse> add(@Query("cargoId") String cargoId,
                                @Query("cargoName") String cargoName,
                                @Query("transporterName") String transporterName,
                                @Query("loadTerminal") String loadTerminal,
                                @Query("unloadTerminal") String unloadTerminal,
                                @Query("tonnage") String tonnage,
                                @Query("tonnageCost") String tonnageCost,
                                @Query("statue") int statue,
                                @Query("remarks") String remarks,
                                @Query("userId") String userId);
}
