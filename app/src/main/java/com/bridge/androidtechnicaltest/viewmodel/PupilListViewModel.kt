package com.bridge.androidtechnicaltest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.repository.IPupilRepository

class PupilListViewModel(private val repository: IPupilRepository) : ViewModel() {

    private val _pupilMutableLiveData = MediatorLiveData<PagedList<Pupil>>()
    val pupilList: LiveData<PagedList<Pupil>>
        get() = _pupilMutableLiveData

    fun fetchPupil() {
        _pupilMutableLiveData.addSource(repository.getOrFetchPupils()) {
            _pupilMutableLiveData.postValue(it)
        }
    }
}