package com.bridge.androidtechnicaltest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.repository.PupilRepository
import com.bridge.androidtechnicaltest.util.DummyDataUtil
import com.bridge.androidtechnicaltest.util.argumentCaptor
import com.bridge.androidtechnicaltest.util.mock
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class PupilListViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var pupilListViewModel: PupilListViewModel
    private val repository = mock(PupilRepository::class.java)

    @Before
    fun setUp() {
        pupilListViewModel = PupilListViewModel(repository)
    }

    @Test
    fun fetchPupil_dataAvailableInCache_verifyList() {
        val pupil = DummyDataUtil.createPupil()
        val pupilLiveData = MutableLiveData<PagedList<Pupil>>()
        pupilLiveData.value = mockPagedList(listOf(pupil))
        `when`(repository.getOrFetchPupils()).thenReturn(pupilLiveData)

        val observer = mock<Observer<PagedList<Pupil>>>()
        pupilListViewModel.pupilList.observeForever(observer)

        pupilListViewModel.fetchPupil()
        val success = argumentCaptor<PagedList<Pupil>>()
        verify(observer).onChanged(success.capture())

        assertNotNull(success.value)
        assertThat(success.value.size, `is`(1))
        assertThat(success.value[0]?.name,`is`("Test"))
    }

    @Test
    fun fetchPupil_dataNotAvailableInCache_verifyList() {
        val pupilLiveData = MutableLiveData<PagedList<Pupil>>()
        pupilLiveData.value = mockPagedList(listOf())
        `when`(repository.getOrFetchPupils()).thenReturn(pupilLiveData)

        val observer = mock<Observer<PagedList<Pupil>>>()
        pupilListViewModel.pupilList.observeForever(observer)

        pupilListViewModel.fetchPupil()
        val success = argumentCaptor<PagedList<Pupil>>()
        verify(observer).onChanged(success.capture())

        assertNotNull(success.value)
        assertThat(success.value.size, `is`(0))
    }

    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = mock(PagedList::class.java) as PagedList<T>
        `when`(pagedList.get(anyInt())).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        `when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }
}