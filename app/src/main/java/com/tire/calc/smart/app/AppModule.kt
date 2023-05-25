package com.tire.calc.smart.app

import android.app.Application
import android.content.SharedPreferences
import com.tire.calc.smart.repositories.DatabaseService
import com.tire.calc.smart.repositories.ManufacturerRepository
import com.tire.calc.smart.ui.main.MainViewModel
import com.tire.calc.smart.ui.search.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }

    single { DatabaseService.getDatabase(androidApplication()).manufacturerDao() }
    single { ManufacturerRepository(get()) }

    single { getSharedPrefs(androidApplication()) }
    single<SharedPreferences.Editor> { getSharedPrefs(androidApplication()).edit() }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences("default", android.content.Context.MODE_PRIVATE)
}
