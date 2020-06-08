package com.bridge.androidtechnicaltest.repository

import com.bridge.androidtechnicaltest.db.PupilDao
import com.bridge.androidtechnicaltest.db.PupilList
import com.bridge.androidtechnicaltest.network.PupilApi
import com.bridge.androidtechnicaltest.util.DummyDataUtil
import com.bridge.androidtechnicaltest.util.any
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class PupilBoundaryCallbackTest {

    private lateinit var pupilBoundaryCallback: PupilBoundaryCallback
    private val pupilDao = Mockito.mock(PupilDao::class.java)
    private val pupilApi = Mockito.mock(PupilApi::class.java)

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        pupilBoundaryCallback = PupilBoundaryCallback(pupilApi, pupilDao)
    }

    @Test
    fun requestAndSaveData_networkSuccessDbSuccess_dataCachedInDb() {
        val pupilList = DummyDataUtil.createPupilList()
        `when`(pupilApi.getPupils(anyInt())).thenReturn(Single.just(PupilList(pupilList)))
        `when`(pupilDao.insertPupils(any())).thenReturn(Completable.complete())

        pupilBoundaryCallback.requestAndSaveData()

        verify(pupilDao, times(1)).insertPupils(pupilList)
        pupilDao.insertPupils(pupilList).test().assertNoErrors()
    }

    @Test
    fun requestAndSaveData_networkFailure_noDataCachedInDb() {
        `when`(pupilApi.getPupils(anyInt())).thenReturn(Single.error(UnknownHostException()))

        pupilBoundaryCallback.requestAndSaveData()

        verify(pupilDao, times(0)).insertPupils(any())
    }

    @Test
    fun requestAndSaveData_requestAlreadyInProgress_noDuplicateRequest() {
        val pupilList = DummyDataUtil.createPupilList()
        `when`(pupilApi.getPupils(anyInt()))
                .thenReturn(Single.just(PupilList(pupilList)).delay(5, TimeUnit.SECONDS))

        pupilBoundaryCallback.requestAndSaveData()
        verify(pupilApi, times(1)).getPupils(anyInt())
        pupilBoundaryCallback.requestAndSaveData()
        verify(pupilApi, times(1)).getPupils(anyInt())
    }
}