package com.tire.calc.smart.ui.modelsize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.models.domain.ModelTrimSizes
import com.tire.calc.smart.models.domain.TireSize
import com.tire.calc.smart.repositories.ModelSizeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class ModelSizeViewModel(
    private val modelSizeRepository: ModelSizeRepository
) : ViewModel() {
    private val _sizes: MutableLiveData<List<ModelTrimSizes>> = MutableLiveData<List<ModelTrimSizes>>()
    val sizes: LiveData<List<ModelTrimSizes>> = _sizes

    fun getSizes(modelId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            modelSizeRepository
                .getModelSizes(modelId)
                .collect {
                    _sizes.postValue(
                        it.groupBy { trimSize -> trimSize.trimName }
                            .map { grouped ->
                                ModelTrimSizes(
                                    trimName = grouped.key,
                                    tireSize = grouped.value.map { size ->
                                        TireSize(
                                            sizeId = size.sizeId,
                                            sizeName = size.sizeName
                                        )
                                    }
                                )
                            }
                    )
                }
        }
    }
}