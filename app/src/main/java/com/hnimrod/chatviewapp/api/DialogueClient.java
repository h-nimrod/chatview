package com.hnimrod.chatviewapp.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hnimrod.chatviewapp.api.common.ApiClientGenerator;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DialogueClient {
    public static final String API_KEY = ""; // Talk APIの API keyを設定してください
    public static final String API_URI = "https://api.a3rt.recruit-tech.co.jp";
    public static final String API_ENDPOINT = "/talk/v1/smalltalk";

    private static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private static DialogueService service = ApiClientGenerator
            .generateMultipart(DialogueService.class, API_URI, gson);

    public static Single<String> getReply(String message) {
        return service.getReply(message, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(item -> Single.just(item.results.get(0).reply));
    }


}
