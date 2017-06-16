package cn.timeface.timefacekit.api;


import java.util.Map;

import cn.timeface.timefacekit.BuildConfig;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by zhangsheng on 2017/6/16.
 */

public interface ApiStores {
    /**
     * 根地址
     */
    String BASE_URL = BuildConfig.API_URL;

    String IMAGE_BASE_URL = "http://img1.timeface.cn/";
    String APP_DOWNLOAD_URL = "http://www.timeface.cn/app.html";

    @POST
    Observable<Object> superRequest(@Url String url,
                                    @QueryMap Map<String, String> params);

}
