package com.tire.calc.smart.ui.selector

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.models.domain.SizeType
import com.tire.calc.smart.repositories.SizesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class SelectorViewModel(private val sizesRepository: SizesRepository) : ViewModel() {

    private val _sizes: MutableLiveData<List<Double>> = MutableLiveData<List<Double>>()
    val sizes: LiveData<List<Double>> = _sizes

    fun getSizes(sizeType: SizeType) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            _sizes.postValue(
                when (sizeType) {
                    SizeType.RimWidth -> sizesRepository.rimWidth
                    SizeType.RimHeight -> sizesRepository.rimHeight
                    SizeType.RimET -> sizesRepository.rimEt
                    SizeType.TireWidth -> sizesRepository.tireWidth
                    SizeType.TireHeight -> sizesRepository.tireHeight
                }.map { it.toDouble() }
            )
        }
    }
}