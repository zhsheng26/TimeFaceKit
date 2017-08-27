package com.watertransport.api;


import com.watertransport.BuildConfig;
import com.watertransport.entity.CargoOrderObj;
import com.watertransport.entity.LoginResponse;
import com.watertransport.entity.PageInfo;

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

    @GET("supply/list")
    Observable<NetResponse<PageInfo<CargoOrderObj>>> list(@Query("pageNo") int pageNo,
                                                          @Query("pageSize") int pageSize,
                                                          @Query("statue") int statue,
                                                          @Query("userId") String userId);

    //status=1发布status=2关闭
    @GET("supply/updateStatue")
    Observable<NetResponse> updateStatue(@Query("id") int id,
                                         @Query("userId") int userId,
                                         @Query("statue") int statue);
}
