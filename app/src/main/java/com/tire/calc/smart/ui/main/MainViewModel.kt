package com.tire.calc.smart.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.models.domain.WheelInfo
import com.tire.calc.smart.models.domain.WheelSize
import com.tire.calc.smart.repositories.SavedSizeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class MainViewModel(
    private val savedSizeRepository: SavedSizeRepository,
) : ViewModel() {

    private val _wheelReference: MutableLiveData<WheelInfo> = MutableLiveData<WheelInfo>()
    val wheelReference: LiveData<WheelInfo> = _wheelReference

    private val _wheelCandidate: MutableLiveData<WheelInfo> = MutableLiveData<WheelInfo>()
    val wheelCandidate: LiveData<WheelInfo> = _wheelCandidate


    init {
        getWheels()
    }

    fun getWheels() {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            savedSizeRepository
                .getReferenceSize()
                .collect { selectedWheelSize ->
                    _wheelReference.postValue(
                        WheelInfo(
                            selectedWheelSize?.let {
                                WheelSize.fromEntity(it.wheelSize)
                            } ?: WheelSize.defaultReference()
                        )
                    )
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            savedSizeRepository
                .getCandidateSize()
                .collect { selectedWheelSize ->
                    _wheelCandidate.postValue(
                        WheelInfo(
                            selectedWheelSize?.let {
                                WheelSize.fromEntity(it.wheelSize)
                            } ?: WheelSize.defaultCandidate()
                        )
                    )
                }
        }
    }
}