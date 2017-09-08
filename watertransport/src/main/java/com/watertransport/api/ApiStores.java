package com.watertransport.api;


import com.watertransport.BuildConfig;
import com.watertransport.entity.BoatHostOrderObj;
import com.watertransport.entity.CargoOrderObj;
import com.watertransport.entity.LoginResponse;
import com.watertransport.entity.MsgObj;
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
    Observable<NetResponse> cargoAdd(@Query("cargoId") String cargoId,
                                     @Query("cargoName") String cargoName,
                                     @Query("transporterName") String transporterName,
                                     @Query("loadTerminal") String loadTerminal,
                                     @Query("unloadTerminal") String unloadTerminal,
                                     @Query("tonnage") String tonnage,
                                     @Query("tonnageCost") String tonnageCost,
                                     @Query("statue") int statue,
                                     @Query("remarks") String remarks,
                                     @Query("userId") String userId);

    //http://59.110.141.52:8080/water_transport/app/supply/update?id=1&userId=1&cargoId=20&cargoName=煤炭&transporterName=合肥长江海运科技有限公司&loadTerminal=马鞍山&unloadTerminal=巢湖&tonnage=1000000&tonnageCost=20&statue=0&remarks=黄花树下不见不散&userId=25
    //statue=0
    @GET("supply/update")
    Observable<NetResponse> cargoUpdate(@Query("id") String id,
                                        @Query("cargoId") String cargoId,
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

    @GET("supply/list")
    Observable<NetResponse<PageInfo<CargoOrderObj>>> boatHostLookList(@Query("pageNo") int pageNo,
                                                                      @Query("pageSize") int pageSize,
                                                                      @Query("statue") int statue);

    //status=1发布status=2关闭
    @GET("supply/updateStatue")
    Observable<NetResponse> updateStatue(@Query("id") String id,
                                         @Query("userId") String userId,
                                         @Query("statue") int statue);

    @GET("userAnnounce/list")
    Observable<NetResponse<PageInfo<MsgObj>>> userMsgList(@Query("pageNo") int pageNo,
                                                          @Query("pageSize") int pageSize);

    /**
     * http://59.110.141.52:8080/water_transport/app/shiperOrder/add?sbipuserId=1&goodsId=2&goodsName=铁
     * &transporter=张三丰&mobile=15600022222&orderId=&loadTime=2017-08-01 00:00:00&unloadTime=2017-08-03 00:00:00
     * &loadCity=铜陵&loadTerminal=岱山码头&unloadCity=巢湖&unloadTerminal=散兵码头&tonnage=1200
     * &settlementTime=2017-08-02 00:00:00&transportCost=20&settlementMoney=24000&orderStatue=0&userId=25
     */
    @GET("shiperOrder/add")
    Observable<NetResponse> boatAddOrder(@Query("sbipuserId") String sbipuserId,
                                         @Query("goodsId") String cargoId,
                                         @Query("goodsName") String cargoName,
                                         @Query("transporter") String transporterName,
                                         @Query("mobile") String mobile,
                                         @Query("loadTime") String loadTime,
                                         @Query("unloadTime") String unloadTime,
                                         @Query("loadCity") String loadCity,
                                         @Query("unloadCity") String unloadCity,
                                         @Query("loadTerminal") String loadTerminal,
                                         @Query("unloadTerminal") String unloadTerminal,
                                         @Query("tonnage") String tonnage,
                                         @Query("settlementTime") String settlementTime,
                                         @Query("transportCost") String transportCost,
                                         @Query("settlementMoney") String settlementMoney,
                                         @Query("remarks") String remarks,
                                         @Query("orderStatue") int orderStatue,
                                         @Query("userId") String userId);

    @GET("shiperOrder/update")
    Observable<NetResponse> boatUpdateOrder(@Query("id") String id,
                                            @Query("goodsId") String cargoId,
                                            @Query("goodsName") String cargoName,
                                            @Query("transporter") String transporterName,
                                            @Query("mobile") String mobile,
                                            @Query("loadTime") String loadTime,
                                            @Query("unloadTime") String unloadTime,
                                            @Query("loadCity") String loadCity,
                                            @Query("unloadCity") String unloadCity,
                                            @Query("loadTerminal") String loadTerminal,
                                            @Query("unloadTerminal") String unloadTerminal,
                                            @Query("tonnage") String tonnage,
                                            @Query("settlementTime") String settlementTime,
                                            @Query("transportCost") String transportCost,
                                            @Query("settlementMoney") String settlementMoney,
                                            @Query("remarks") String remarks);

    //    http://59.110.141.52:8080/water_transport/app/shiperOrder/list?userId=&orderStatue=&pageNo=1&pageSize=10
    @GET("shiperOrder/list")
    Observable<NetResponse<PageInfo<BoatHostOrderObj>>> boatOrderList(@Query("userId") String userId,
                                                                      @Query("orderStatue") int orderStatue,
                                                                      @Query("pageNo") int pageNo,
                                                                      @Query("pageSize") int pageSize);

    @GET("shiperOrder/updateShipOderStatue")
    Observable<NetResponse> updateShipOderStatue(@Query("userId") String userId,
                                                 @Query("id") String id);

}
