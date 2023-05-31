package com.tire.calc.smart.app

import com.tire.calc.smart.repositories.DatabaseService
import com.tire.calc.smart.repositories.FavoriteWheelRepository
import com.tire.calc.smart.repositories.ManufacturerModelRepository
import com.tire.calc.smart.repositories.TrimWheelSizeRepository
import com.tire.calc.smart.repositories.SelectedWheelRepository
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
    viewModel { WheelSizeViewModel(get(), get()) }
    viewModel { WheelsViewModel(get()) }
    viewModel { SelectorViewModel(get()) }

    single { DatabaseService.getDatabase(androidApplication()).manufacturerDao() }
    single { DatabaseService.getDatabase(androidApplication()).modelDao() }
    single { DatabaseService.getDatabase(androidApplication()).trimDao() }
    single { DatabaseService.getDatabase(androidApplication()).wheelDao() }
    single { DatabaseService.getDatabase(androidApplication()).trimWheelDao() }
    single { DatabaseService.getDatabase(androidApplication()).manufacturerModelDao() }
    single { DatabaseService.getDatabase(androidApplication()).trimWheelSizeDao() }
    single { DatabaseService.getDatabase(androidApplication()).selectedSizeDao() }
    single { DatabaseService.getDatabase(androidApplication()).favoriteWheelDao() }

    single { ManufacturerModelRepository(get()) }
    single { TrimWheelSizeRepository(get()) }
    single { FavoriteWheelRepository(get(), get(), get(), get()) }
    single { SelectedWheelRepository(get(), get(), get(), get()) }
    single { SizesRepository() }
}
