package com.hnimrod.chatviewapp.api;


import com.hnimrod.chatviewapp.model.DialogueReplyEntity;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DialogueService {

    @FormUrlEncoded
    @POST(DialogueClient.API_ENDPOINT)
    Single<DialogueReplyEntity> getReply(
            @Field("query") String query,
            @Field("apikey") String apikey
    );
}
