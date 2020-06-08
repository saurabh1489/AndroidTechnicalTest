package com.bridge.androidtechnicaltest.workmanager

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.bridge.androidtechnicaltest.db.AppDatabase
import com.bridge.androidtechnicaltest.network.PupilApi
import io.reactivex.Completable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadWorker(
        context: Context,
        params: WorkerParameters
) : RxWorker(context, params), KoinComponent {

    private val db by inject<AppDatabase>()
    private val api by inject<PupilApi>()

    override fun createWork(): Single<Result> {
        Log.d("UploadWorker", "createWork()")
        return syncUserData()
                .doOnComplete {
                    Log.e("UploadWorker", "Upload success")
                    db.pupilDao.clearCache()
                }
                .toSingleDefault(
                        Result.success()
                ).onErrorReturn {
                    Log.e("UploadWorker", "Error ${it.message}")
                    Result.failure()
                }
    }

    private fun syncUserData(): Completable {
        return db.pupilDao.cachePupils
                .flattenAsFlowable { it }
                .map { it.toPupil() }
                .parallel()
                .map { api.createPupil(it) }
                .sequential()
                .ignoreElements()
    }
}