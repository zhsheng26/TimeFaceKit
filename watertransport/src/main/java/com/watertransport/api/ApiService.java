package com.watertransport.api;

import cn.timeface.timekit.support.ApiClient;

/**
 * Created by zhangsheng on 2017/6/16.
 */

public class ApiService {

    private final ApiStores apiStores;

    private ApiService() {
        ApiClient apiClient = new ApiClient();
        apiStores = apiClient.getApiStores(ApiStores.BASE_URL, ApiStores.class);
    }

    private static class ApiServiceHolder {
        private static final ApiService apiService = new ApiService();
    }

    public static ApiService getInstance() {
        return ApiServiceHolder.apiService;
    }

    public ApiStores getApi() {
        return apiStores;
    }
}
