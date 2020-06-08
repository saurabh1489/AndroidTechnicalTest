package com.bridge.androidtechnicaltest.network

import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.db.PupilList
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PupilApi {
    @GET("pupils")
    fun getPupils(@Query("page") page: Int = 1): Single<PupilList>

    @POST("pupils")
    fun createPupil(@Body pupil: Pupil): Single<Pupil>
}