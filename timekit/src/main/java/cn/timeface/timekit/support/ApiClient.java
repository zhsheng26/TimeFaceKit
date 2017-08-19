package cn.timeface.timekit.support;

import android.support.annotation.NonNull;
import android.util.Log;

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
import timber.log.Timber;

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
                    Log.i("HEADER", entry.getKey() + " : " + entry.getValue());
                }
                Response response = chain.proceed(req.build());
                boolean gzip = response.headers().get("Data-Type") != null && response.headers().get("Data-Type").equals("gzip");
                if (gzip) {
                    return response.newBuilder().removeHeader("Data-Type").addHeader("Content-Encoding", "gzip").build();
                } else {
                    return response.newBuilder().build();
                }
            }
        });

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.tag("Http").i(message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(loggingInterceptor);
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