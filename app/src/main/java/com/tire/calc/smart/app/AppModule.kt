package com.tire.calc.smart.app

import android.app.Application
import android.content.SharedPreferences
import com.tire.calc.smart.repositories.DatabaseService
import com.tire.calc.smart.repositories.ManufacturerModelRepository
import com.tire.calc.smart.repositories.ModelSizeRepository
import com.tire.calc.smart.repositories.SavedSizeRepository
import com.tire.calc.smart.repositories.SizesRepository
import com.tire.calc.smart.ui.main.MainViewModel
import com.tire.calc.smart.ui.search.wheels.WheelsViewModel
import com.tire.calc.smart.ui.search.models.ModelsViewModel
import com.tire.calc.smart.ui.selector.SelectorViewModel
import com.tire.calc.smart.ui.wheelsize.WheelSizeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ModelsViewModel(get(), get()) }
    viewModel { WheelSizeViewModel(get()) }
    viewModel { WheelsViewModel(get()) }
    viewModel { SelectorViewModel(get()) }

    single { DatabaseService.getDatabase(androidApplication()).wheelDao() }
    single { DatabaseService.getDatabase(androidApplication()).trimWheelDao() }
    single { DatabaseService.getDatabase(androidApplication()).manufacturerModelDao() }
    single { DatabaseService.getDatabase(androidApplication()).modelSizeDao() }
    single { DatabaseService.getDatabase(androidApplication()).selectedSizeDao() }
    single { DatabaseService.getDatabase(androidApplication()).favoriteWheelDao() }

    single { ManufacturerModelRepository(get()) }
    single { ModelSizeRepository(get()) }
    single { SavedSizeRepository(get(), get(), get(), get()) }
    single { SizesRepository() }

    single { getSharedPrefs(androidApplication()) }
    single<SharedPreferences.Editor> { getSharedPrefs(androidApplication()).edit() }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences("default", android.content.Context.MODE_PRIVATE)
}
