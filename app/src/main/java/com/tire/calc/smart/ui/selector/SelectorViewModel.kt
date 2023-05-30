package com.tire.calc.smart.ui.selector

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.app.Constants
import com.tire.calc.smart.repositories.SizesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class SelectorViewModel(private val sizesRepository: SizesRepository) : ViewModel() {

    private val _sizes: MutableLiveData<List<Double>> = MutableLiveData<List<Double>>()
    val sizes: LiveData<List<Double>> = _sizes

    fun getSizes(sizeType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            _sizes.postValue(
                when (sizeType) {
                    Constants.SIZE_TIRE_WIDTH -> sizesRepository.tireWidth
                    Constants.SIZE_TIRE_HEIGHT -> sizesRepository.tireHeight
                    Constants.SIZE_RIM_WIDTH -> sizesRepository.rimWidth
                    Constants.SIZE_RIM_HEIGHT -> sizesRepository.rimHeight
                    Constants.SIZE_RIM_ET -> sizesRepository.rimEt
                    else -> emptyList()
                }.map { it.toDouble() }
            )
        }
    }
}