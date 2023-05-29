package com.tire.calc.smart.ui.size

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.app.Constants
import com.tire.calc.smart.models.domain.WheelSize
import com.tire.calc.smart.repositories.SavedSizeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class SizeViewModel(
    private val savedSizeRepository: SavedSizeRepository,
) : ViewModel() {
    private val _wheel: MutableLiveData<WheelSize> = MutableLiveData<WheelSize>()
    val wheel: LiveData<WheelSize> = _wheel

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getWheel(wheelSelected: String) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            when (wheelSelected) {
                Constants.SELECTED_WHEEL_REFERENCE ->
                    savedSizeRepository
                        .getReferenceSize()
                        .collect {
                            _wheel.postValue(WheelSize.fromEntity(it.size))
                        }
                Constants.SELECTED_WHEEL_CANDIDATE ->
                    savedSizeRepository
                        .getCandidateSize()
                        .collect {
                            _wheel.postValue(WheelSize.fromEntity(it.size))
                        }
            }
        }
    }

    fun checkIsFavorite(wheelSize: WheelSize) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            savedSizeRepository
                .getFavorites()
                .collect { tireSizes ->
                    _isFavorite.postValue(
                        tireSizes
                            .map { it.size }
                            .contains(wheelSize.toEntity())
                    )
                }
        }
    }

    fun setFavorite() {
        // TODO:
    }

}