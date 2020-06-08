package com.bridge.androidtechnicaltest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.repository.IPupilRepository

class PupilDetailViewModel(private val repository: IPupilRepository): ViewModel() {
    private val _pupilLiveData = MutableLiveData<Long>()
    val pupilLiveData: LiveData<Pupil> = Transformations.switchMap(_pupilLiveData) {
        repository.getPupilDetail(it)
    }

    fun getPupilDetail(pupilId: Long) {
        _pupilLiveData.value = pupilId
    }
}