package cn.timeface.timekit.support;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.timeface.timekit.BuildConfig;
import cn.timeface.timekit.util.storage.Remember;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangsheng on 2017/3/5.
 */

public class ApiClient {

    public <T> T getApiStores(String baseUrl, Class<T> cls) {
        return getApiRetrofit(baseUrl).create(cls);
    }

    private Retrofit getApiRetrofit(String baseUrl) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS);

        httpClientBuilder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request.Builder req = chain.request().newBuilder();
                for (Map.Entry<String, String> entry : getHttpHeaders().entrySet()) {
                    req.addHeader(entry.getKey(), entry.getValue());
                }
                return chain.proceed(req.build());
            }
        });

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient okHttpClient = httpClientBuilder.build();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ToStringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static Map<String, String> getHttpHeaders() {
        Map<String, String> headers = new HashMap<>(7);
        headers.put("LOC", "CN");
        headers.put("OUTFLAG", "JSON");
        headers.put("USERID", Remember.getString("userId", ""));
        headers.put("DEVICEID", DeviceUuidFactory.getDeviceUuid().toString());
        headers.put("VERSION", BuildConfig.VERSION_CODE + "");
        return headers;
    }

}