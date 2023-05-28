package com.tire.calc.smart.ui.modelsize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.models.TireSize
import com.tire.calc.smart.models.TrimSizes
import com.tire.calc.smart.repositories.ModelSizeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class ModelSizeViewModel(
    private val modelSizeRepository: ModelSizeRepository
) : ViewModel() {
    private val _sizes: MutableLiveData<List<TrimSizes>> = MutableLiveData<List<TrimSizes>>()
    val sizes: LiveData<List<TrimSizes>> = _sizes

    fun getSizes(modelId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            modelSizeRepository
                .getModelSizes(modelId)
                .collect {
                    _sizes.postValue(
                        it.groupBy { trimSize -> trimSize.trimName }
                            .map { grouped ->
                                TrimSizes(
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