package com.hnimrod.chatviewapp.api;


import com.hnimrod.chatviewapp.model.DialogueEntity;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DialogueService {
    @POST(DialogueClient.API_ENDPOINT)
    Single<DialogueEntity> getReply(@Body DialogueEntity body);
}
