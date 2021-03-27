package com.hnimrod.chatviewapp.api.common;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;


public class CommonClient {

    private static final int RETRY_COUNT = 3;
    private static final int RETRY_DELAY = 2;

    private static final boolean AUTO_RETRY = true;

    public static <T> Observable<T> retry (Observable<T> observable) {
        if (AUTO_RETRY) {
            return retryAuto(observable);
        } else {
            return retryNothing(observable);
        }
    }

    public static <T> Observable<T> retryAuto(Observable<T> observable) {
        return observable
                .retryWhen(attempts -> attempts.zipWith(Observable.range(1, RETRY_COUNT+1), (error, i) -> i).flatMap((Integer i) -> {
                    if (i <= RETRY_COUNT) {
                        return Observable.timer((RETRY_DELAY * i), TimeUnit.SECONDS);
                    }
                    return attempts.flatMap(Observable::error);
                }));
    }

    public static <T> Observable<T> retryNothing(Observable<T> observable) {
        return observable;
    }
}
