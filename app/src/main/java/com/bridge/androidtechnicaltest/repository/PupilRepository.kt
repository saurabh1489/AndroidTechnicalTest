package com.bridge.androidtechnicaltest.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bridge.androidtechnicaltest.db.AppDatabase
import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.network.PupilApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

interface IPupilRepository {
    fun getOrFetchPupils(): LiveData<PagedList<Pupil>>
    fun getPupilDetail(pupilId: Long): LiveData<Pupil>
    fun createPupil(pupil: Pupil): Single<Pupil>
}

class PupilRepository(private val database: AppDatabase, private val pupilApi: PupilApi) : IPupilRepository {

    override fun getOrFetchPupils(): LiveData<PagedList<Pupil>> {
        val dataSourceFactory = database.pupilDao.pupils
        val boundaryCallback = PupilBoundaryCallback(pupilApi, database.pupilDao)

        return LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
    }

    override fun getPupilDetail(pupilId: Long): LiveData<Pupil> {
        return database.pupilDao.getPupil(pupilId)
    }

    override fun createPupil(pupil: Pupil): Single<Pupil> {
        return pupilApi.createPupil(pupil).doOnError { throwable ->
            if (throwable is UnknownHostException) {
                database.pupilDao.insertPupil(pupil.toCache())
            }
        }.subscribeOn(Schedulers.io())
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}