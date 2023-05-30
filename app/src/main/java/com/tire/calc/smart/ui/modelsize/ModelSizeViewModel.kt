package com.tire.calc.smart.ui.modelsize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.models.domain.TrimWheels
import com.tire.calc.smart.models.domain.Wheel
import com.tire.calc.smart.repositories.ModelSizeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class ModelSizeViewModel(
    private val modelSizeRepository: ModelSizeRepository
) : ViewModel() {
    private val _sizes: MutableLiveData<List<TrimWheels>> = MutableLiveData<List<TrimWheels>>()
    val sizes: LiveData<List<TrimWheels>> = _sizes

    fun getSizes(modelId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            modelSizeRepository
                .getModelSizes(modelId)
                .collect {
                    _sizes.postValue(
                        it.groupBy { trimSize -> trimSize.trimName }
                            .map { grouped ->
                                TrimWheels(
                                    name = grouped.key,
                                    wheels = grouped.value.map { trimWheelSize ->
                                        Wheel(
                                            id = trimWheelSize.wheelId,
                                            size = trimWheelSize.wheelSize
                                        )
                                    }
                                )
                            }
                    )
                }
        }
    }
}