package cn.timeface.timefacekit.api;


import cn.timeface.timefacekit.BuildConfig;
import cn.timeface.timefacekit.api.entity.response.LoginResponse;
import cn.timeface.timekit.support.NetResponse;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    /**
     * 用户登录
     */
    @FormUrlEncoded
    @POST("auth/userLogin")
    Observable<LoginResponse> login(@Field("account") String account,
                                    @Field("password") String password);


}
