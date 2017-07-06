package com.example.codemac.retropost;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by codemac on 16/6/17.
 */

public class RetroClient {
    private static final String ROOT_URL = "http:///174.138.48.117/";

    private static Retrofit getRetrofitInstance() {



        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
