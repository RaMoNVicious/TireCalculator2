package com.tire.calc.smart.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.R
import com.tire.calc.smart.models.domain.SelectedWheel
import com.tire.calc.smart.models.domain.WheelCompare
import com.tire.calc.smart.models.domain.WheelInfo
import com.tire.calc.smart.models.domain.WheelSize
import com.tire.calc.smart.repositories.SelectedWheelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class MainViewModel(
    private val selectedWheelRepository: SelectedWheelRepository,
) : ViewModel() {

    private val _wheelReference: MutableLiveData<WheelInfo> = MutableLiveData<WheelInfo>()
    val wheelReference: LiveData<WheelInfo> = _wheelReference

    private val _wheelCandidate: MutableLiveData<WheelInfo> = MutableLiveData<WheelInfo>()
    val wheelCandidate: LiveData<WheelInfo> = _wheelCandidate

    val wheelCompare: MediatorLiveData<List<WheelCompare>> = MediatorLiveData<List<WheelCompare>>()

    init {
        wheelCompare.addSource(_wheelReference) {
            setWheels(_wheelReference, _wheelCandidate)
        }
        wheelCompare.addSource(_wheelCandidate) {
            setWheels(_wheelReference, _wheelCandidate)
        }

        getWheels()
    }

    fun getWheels() {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            selectedWheelRepository
                .getSelectedSize(SelectedWheel.Reference.id)
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
            selectedWheelRepository
                .getSelectedSize(SelectedWheel.Candidate.id)
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

    private fun setWheels(reference: LiveData<WheelInfo>, candidate: LiveData<WheelInfo>) {
        val valueReference = reference.value
        val valueCandidate = candidate.value

        if (valueReference != null && valueCandidate != null) {
            wheelCompare.postValue(
                listOf(
                    WheelCompare(
                        R.string.compare_tire_width,
                        valueReference.getTireWidth(),
                        valueCandidate.getTireWidth()
                    ),
                    WheelCompare(
                        R.string.compare_tire_side_height,
                        valueReference.getTireSideHeight(),
                        valueCandidate.getTireSideHeight()
                    ),
                    WheelCompare(
                        R.string.compare_wheel_height,
                        valueReference.getWheelHeight(),
                        valueCandidate.getWheelHeight()
                    ),
                    WheelCompare(
                        R.string.compare_wheel_circumference,
                        valueReference.getWheelCircumference(),
                        valueCandidate.getWheelCircumference()
                    ),
                    WheelCompare(
                        R.string.compare_wheel_revs_per_km,
                        valueReference.getRevsPer(),
                        valueCandidate.getRevsPer()
                    ),
                )
            )
        }
    }
}