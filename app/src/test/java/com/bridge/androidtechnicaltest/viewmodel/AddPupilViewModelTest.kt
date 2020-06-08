package com.bridge.androidtechnicaltest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bridge.androidtechnicaltest.repository.PupilRepository
import com.bridge.androidtechnicaltest.util.DummyDataUtil
import com.bridge.androidtechnicaltest.util.any
import com.bridge.androidtechnicaltest.util.argumentCaptor
import com.bridge.androidtechnicaltest.util.mock
import com.bridge.androidtechnicaltest.vo.Resource
import com.bridge.androidtechnicaltest.vo.Status
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.verify
import java.lang.IllegalArgumentException
import java.util.concurrent.Callable


@RunWith(JUnit4::class)
class AddPupilViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var pupilViewModel: AddPupilViewModel
    private val repository = Mockito.mock(PupilRepository::class.java)

    @Before
    fun setUp() {
        pupilViewModel = AddPupilViewModel(repository)
    }

    @Test
    fun createPupil_success_observersNotified() {
        val pupil = DummyDataUtil.createPupil()
        Mockito.`when`(repository.createPupil(any())).thenReturn(
                Single.just(pupil)
        )

        val observer = mock<Observer<Resource<Void>>>()
        pupilViewModel.uploadStatusLiveData.observeForever(observer)
        pupilViewModel.createPupil(pupil)

        val success = argumentCaptor<Resource<Void>>()
        verify(observer).onChanged(success.capture())

        assertThat(success.value.status, `is`(Status.SUCCESS))
    }


    @Test
    fun createPupil_error_observersNotified() {
        val pupil = DummyDataUtil.createPupil()
        Mockito.`when`(repository.createPupil(any())).thenReturn(
                Single.error(IllegalArgumentException())
        )

        val observer = mock<Observer<Resource<Void>>>()
        pupilViewModel.uploadStatusLiveData.observeForever(observer)
        pupilViewModel.createPupil(pupil)

        val success = argumentCaptor<Resource<Void>>()
        verify(observer).onChanged(success.capture())

        assertThat(success.value.status, `is`(Status.ERROR))
    }
}