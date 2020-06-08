package com.bridge.androidtechnicaltest.repository

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.paging.PagedList
import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.db.PupilDao
import com.bridge.androidtechnicaltest.db.PupilList
import com.bridge.androidtechnicaltest.network.PupilApi
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

class PupilBoundaryCallback(
        private val pupilApi: PupilApi,
        private val pupilDao: PupilDao
) : PagedList.BoundaryCallback<Pupil>() {

    private val TAG = "PupilBoundaryCallback"
    private var lastRequestedPage = 1
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        Log.d(TAG, "onZeroItemsLoaded")
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Pupil) {
        Log.d(TAG, "onItemAtEndLoaded")
        requestAndSaveData()
    }

    @VisibleForTesting
    fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        pupilApi.getPupils(lastRequestedPage)
                .subscribeOn(Schedulers.io())
                .doOnError {
                    isRequestInProgress = false
                }
                .flatMapCompletable { t ->
                    pupilDao.insertPupils(t.items)
                            .doOnComplete {
                                isRequestInProgress = false
                                lastRequestedPage++
                            }
                }
                .subscribe(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        dispose()
                    }

                    override fun onError(e: Throwable) {
                        dispose()
                    }

                })

    }
}