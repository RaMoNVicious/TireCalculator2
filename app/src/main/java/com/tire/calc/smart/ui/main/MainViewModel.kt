package com.tire.calc.smart.ui.main

import android.app.Application
import android.content.SharedPreferences
import android.os.Debug
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.models.SearchManufacturer
import com.tire.calc.smart.models.domain.WheelInfo
import com.tire.calc.smart.models.domain.WheelSize
import com.tire.calc.smart.models.dao.Manufacturer
import com.tire.calc.smart.repositories.DatabaseService
import com.tire.calc.smart.repositories.ManufacturerRepository
import com.tire.calc.smart.repositories.SavedSizeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class MainViewModel(
    private val application: Application,
    private val savedSizeRepository: SavedSizeRepository,
) : ViewModel() {

    private val repository: ManufacturerRepository

    val allManufacturers: LiveData<List<Manufacturer>>

    val wheelInfo = MutableLiveData<WheelInfo>()

    private val _wheelReference: MutableLiveData<WheelInfo> = MutableLiveData<WheelInfo>()
    val wheelReference: LiveData<WheelInfo> = _wheelReference

    private val _wheelCandidate: MutableLiveData<WheelInfo> = MutableLiveData<WheelInfo>()
    val wheelCandidate: LiveData<WheelInfo> = _wheelCandidate


    init {
        val manufacturer = DatabaseService.getDatabase(application).manufacturerDao()
        val model = DatabaseService.getDatabase(application).modelDao()
        repository = ManufacturerRepository(manufacturer)
        allManufacturers = repository.allManufacturers

        getWheels()
    }

    fun getWheels() {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            savedSizeRepository
                .getReferenceSize()
                .collect {
                    Log.d("WHEEL GET REFERENCE", it.size)
                    _wheelReference.postValue(WheelInfo(WheelSize.fromEntity(it.size)))
                }
            savedSizeRepository
                .getCandidateSize()
                .collect {
                    Log.d("WHEEL GET CANDIDATE", it.size)
                    _wheelCandidate.postValue(WheelInfo(WheelSize.fromEntity(it.size)))
                }
        }
    }

    /*fun updateCarInfo() =
        viewModelScope.launch {
            *//*val wheelSize = WheelSize(
                tireWidth = 205.0,
                tireHeight = 55.0,
                rimHeight = 16.0,
                rimWidth = 7.0,
                rimEt = 38.0,
            )*//*
            val wheelSize = WheelSize(
                tireWidth = 205.0,
                tireHeight = 55.0,
                rimHeight = 16.0,
                rimWidth = 7.0,
                rimEt = 38.0,
            )

            wheelInfo.postValue(WheelInfo(wheelSize))
        }*/
}