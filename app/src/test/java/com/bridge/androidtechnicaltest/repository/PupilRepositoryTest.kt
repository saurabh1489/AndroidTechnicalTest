package com.bridge.androidtechnicaltest.repository

import androidx.lifecycle.MutableLiveData
import com.bridge.androidtechnicaltest.db.AppDatabase
import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.db.PupilDao
import com.bridge.androidtechnicaltest.network.PupilApi
import com.bridge.androidtechnicaltest.util.DummyDataUtil
import com.bridge.androidtechnicaltest.util.any
import com.bridge.androidtechnicaltest.util.mock
import io.reactivex.Single
import io.reactivex.functions.Predicate
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.*
import java.net.UnknownHostException

@RunWith(JUnit4::class)
class PupilRepositoryTest {

    private lateinit var pupilRepository: PupilRepository
    private val database = mock(AppDatabase::class.java)
    private val api = mock(PupilApi::class.java)

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        pupilRepository = PupilRepository(database, api)
    }

    @Test
    fun getPupilDetail_verifyDaoCall() {
        val pupilDao = mock<PupilDao>()
        `when`(database.pupilDao).thenReturn(pupilDao)
        `when`(pupilDao.getPupil(anyLong())).thenReturn(MutableLiveData())
        pupilRepository.getPupilDetail(1234)

        verify(database.pupilDao, times(1)).getPupil(1234)
    }

    @Test
    fun createPupil_success_notifySubscriber() {
        val pupil = DummyDataUtil.createPupil()
        `when`(api.createPupil(any())).thenReturn(Single.just(pupil))
        val testObserver = pupilRepository.createPupil(pupil).test()

        testObserver.assertNoErrors().assertValue { p -> p == pupil }
    }

    @Test
    fun createPupil_error_insertToDb() {
        val pupil = DummyDataUtil.createPupil()
        val pupilDao = mock<PupilDao>()
        `when`(database.pupilDao).thenReturn(pupilDao)
        `when`(api.createPupil(any())).thenReturn(Single.error(UnknownHostException()))
        val testObserver = pupilRepository.createPupil(pupil).test()

        testObserver.assertError(UnknownHostException::class.java)
        verify(pupilDao, times(1)).insertPupil(any())
    }

    @Test
    fun getOrFetchPupils_error_insertToDb() {
        val pupil = DummyDataUtil.createPupil()
        val pupilDao = mock<PupilDao>()
        `when`(database.pupilDao).thenReturn(pupilDao)
        `when`(api.createPupil(any())).thenReturn(Single.error(UnknownHostException()))
        val testObserver = pupilRepository.createPupil(pupil).test()

        testObserver.assertError(UnknownHostException::class.java)
        verify(pupilDao, times(1)).insertPupil(any())
    }

}