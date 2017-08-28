package com.hnimrod.chatviewapp.api.common;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientGenerator {

    public static <T> T generate(Class<T> clazz, OkHttpClient httpClient, String baseUrl) {
        return new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clazz);
    }


    public static <T> T generate(Class<T> clazz, OkHttpClient httpClient, String baseUrl, Gson gson) {
        return new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(clazz);
    }

    public static <T> T generateMultipart(Class<T> clazz, String baseUrl, Gson gson) {
        return new Retrofit.Builder()
                .client(HttpClient.getInstance())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(clazz);
    }

}
