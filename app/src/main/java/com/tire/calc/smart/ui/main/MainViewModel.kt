package com.tire.calc.smart.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.models.WheelInfo
import com.tire.calc.smart.models.WheelSize
import kotlinx.coroutines.launch

class MainViewModel(
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {
    val wheelInfo = MutableLiveData<WheelInfo>()

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