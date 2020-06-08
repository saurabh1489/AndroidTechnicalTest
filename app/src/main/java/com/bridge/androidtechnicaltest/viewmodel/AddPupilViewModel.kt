package com.bridge.androidtechnicaltest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.repository.IPupilRepository
import com.bridge.androidtechnicaltest.vo.Resource
import com.bridge.androidtechnicaltest.workmanager.UploadWorker
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

class AddPupilViewModel(private val pupilRepository: IPupilRepository) : ViewModel() {

    private val subscriptions = CompositeDisposable()
    private val _uploadStatus = MutableLiveData<Resource<Void>>()
    val uploadStatusLiveData: LiveData<Resource<Void>>
        get() = _uploadStatus

    fun createPupil(pupil: Pupil) {
        val subscription = pupilRepository.createPupil(pupil).subscribe({
            _uploadStatus.postValue(Resource.success(null))
        }, { exception ->
            if (exception !is UnknownHostException) {
                _uploadStatus.postValue(Resource.error(exception.message, null))
            } else {
                _uploadStatus.postValue(Resource.error("Record cached. Will be uploaded when network available", null))
                enqueueRequest()
            }
        })
        subscriptions.add(subscription)
    }

    private fun enqueueRequest() {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val syncDataRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance().enqueue(syncDataRequest)
    }

    override fun onCleared() {
        subscriptions.dispose()
    }

}