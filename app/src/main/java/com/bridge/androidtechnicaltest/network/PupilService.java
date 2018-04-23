package com.bridge.androidtechnicaltest.network;

import io.reactivex.Completable;
import retrofit2.http.POST;

public interface PupilService {
    @POST("reset")
    Completable reset();
}
