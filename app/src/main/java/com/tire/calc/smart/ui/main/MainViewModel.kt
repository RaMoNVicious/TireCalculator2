package com.tire.calc.smart.ui.main

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.models.WheelInfo
import com.tire.calc.smart.models.WheelSize
import com.tire.calc.smart.models.dao.Manufacturer
import com.tire.calc.smart.repositories.DatabaseService
import com.tire.calc.smart.repositories.ManufacturerRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val application: Application,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val repository: ManufacturerRepository

    val allManufacturers: LiveData<List<Manufacturer>>

    val wheelInfo = MutableLiveData<WheelInfo>()


    init {
        val manufacturer = DatabaseService.getDatabase(application).manufacturerDao()
        val model = DatabaseService.getDatabase(application).modelDao()
        repository = ManufacturerRepository(manufacturer)
        allManufacturers = repository.allManufacturers
    }

    fun updateCarInfo() =
        viewModelScope.launch {
            /*val wheelSize = WheelSize(
                tireWidth = 205.0,
                tireHeight = 55.0,
                rimHeight = 16.0,
                rimWidth = 7.0,
                rimEt = 38.0,
            )*/
            val wheelSize = WheelSize(
                tireWidth = 205.0,
                tireHeight = 55.0,
                rimHeight = 16.0,
                rimWidth = 7.0,
                rimEt = 38.0,
            )

            wheelInfo.postValue(WheelInfo(wheelSize))
        }
}