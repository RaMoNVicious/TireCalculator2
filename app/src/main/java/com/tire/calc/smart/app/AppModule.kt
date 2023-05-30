package com.tire.calc.smart.app

import android.app.Application
import android.content.SharedPreferences
import com.tire.calc.smart.repositories.DatabaseService
import com.tire.calc.smart.repositories.ManufacturerModelRepository
import com.tire.calc.smart.repositories.ModelSizeRepository
import com.tire.calc.smart.repositories.SavedSizeRepository
import com.tire.calc.smart.repositories.SizesRepository
import com.tire.calc.smart.ui.main.MainViewModel
import com.tire.calc.smart.ui.modelsize.ModelSizeViewModel
import com.tire.calc.smart.ui.search.SearchViewModel
import com.tire.calc.smart.ui.selector.SelectorViewModel
import com.tire.calc.smart.ui.size.SizeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { SizeViewModel(get()) }
    viewModel { ModelSizeViewModel(get()) }
    viewModel { SelectorViewModel(get()) }

    single { DatabaseService.getDatabase(androidApplication()).tireSizeDao() }
    single { DatabaseService.getDatabase(androidApplication()).manufacturerModelDao() }
    single { DatabaseService.getDatabase(androidApplication()).modelSizeDao() }
    single { DatabaseService.getDatabase(androidApplication()).selectedSizeDao() }

    single { ManufacturerModelRepository(get()) }
    single { ModelSizeRepository(get()) }
    single { SavedSizeRepository(get(), get()) }
    single { SizesRepository() }

    single { getSharedPrefs(androidApplication()) }
    single<SharedPreferences.Editor> { getSharedPrefs(androidApplication()).edit() }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences("default", android.content.Context.MODE_PRIVATE)
}
