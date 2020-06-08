package com.bridge.androidtechnicaltest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.repository.PupilRepository
import com.bridge.androidtechnicaltest.util.DummyDataUtil
import com.bridge.androidtechnicaltest.util.any
import com.bridge.androidtechnicaltest.util.argumentCaptor
import com.bridge.androidtechnicaltest.util.mock
import com.bridge.androidtechnicaltest.vo.Resource
import com.bridge.androidtechnicaltest.vo.Status
import io.reactivex.Single
import junit.framework.Assert.assertNotNull
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt

@RunWith(JUnit4::class)
class PupilDetailViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var pupilDetailViewModel: PupilDetailViewModel
    private val repository = Mockito.mock(PupilRepository::class.java)

    @Before
    fun setUp() {
        pupilDetailViewModel = PupilDetailViewModel(repository)
    }

    @Test
    fun getPupilDetail_success_observersNotified() {
        val pupil = DummyDataUtil.createPupil()
        val pupilLiveData = MutableLiveData<Pupil>()
        pupilLiveData.value = pupil
        `when`(repository.getPupilDetail(anyLong())).thenReturn(pupilLiveData)

        val observer = mock<Observer<Pupil>>()
        pupilDetailViewModel.pupilLiveData.observeForever(observer)
        pupilDetailViewModel.getPupilDetail(12345)

        val success = argumentCaptor<Pupil>()
        Mockito.verify(observer).onChanged(success.capture())

        assertNotNull(success.value)
    }
}