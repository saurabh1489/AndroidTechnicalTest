package com.bridge.androidtechnicaltest.di

import com.bridge.androidtechnicaltest.repository.IPupilRepository
import com.bridge.androidtechnicaltest.repository.PupilRepository
import com.bridge.androidtechnicaltest.viewmodel.AddPupilViewModel
import com.bridge.androidtechnicaltest.viewmodel.PupilDetailViewModel
import com.bridge.androidtechnicaltest.viewmodel.PupilListViewModel
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

val networkModule = module {
    factory { PupilAPIFactory.retrofitPupil() }
}

val databaseModule = module {
    factory { DatabaseFactory.getDBInstance(get()) }
    single<IPupilRepository>{ PupilRepository(get(), get()) }
}

val viewModelModule  = module {
    viewModel { PupilListViewModel(get()) }
    viewModel { PupilDetailViewModel(get()) }
    viewModel { AddPupilViewModel(get()) }
}

