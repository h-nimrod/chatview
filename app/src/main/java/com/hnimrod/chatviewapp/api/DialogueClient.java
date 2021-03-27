package com.hnimrod.chatviewapp.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.hnimrod.chatviewapp.api.common.ApiClientGenerator;
import com.hnimrod.chatviewapp.model.DialogueEntity;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DialogueClient {
    public static final String API_KEY = ""; // Docomo 雑談対話 API keyを設定してください
    public static final String API_URI = "https://api.apigw.smt.docomo.ne.jp";
    public static final String API_ENDPOINT = "/dialogue/v1/dialogue?" + API_KEY;

    private static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private static DialogueService service = ApiClientGenerator
            .generateMultipart(DialogueService.class, API_URI, gson);

    private static DialogueEntity request = new DialogueEntity();
    public static Single<String> getReply(String message) {
        request.utt = message;
        return service.getReply(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(item -> {
                    request.context = item.context;
                    request.mode = item.mode;
                })
                .flatMap(item -> Single.just(item.utt));
    }


}
