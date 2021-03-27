package com.hnimrod.chatviewapp.api.common;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClient {

    private static OkHttpClient instance;

    private HttpClient() {}

    public static synchronized OkHttpClient getInstance() {
        return getInstance(HttpLoggingInterceptor.Level.BODY);
    }

    public static synchronized OkHttpClient getInstance(HttpLoggingInterceptor.Level logLevel) {
        if (instance == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(logLevel);
            instance = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();
        }

        return instance;
    }

}
