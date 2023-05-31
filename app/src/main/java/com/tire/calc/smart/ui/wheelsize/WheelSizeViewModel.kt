package com.tire.calc.smart.ui.wheelsize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.app.Constants
import com.tire.calc.smart.models.domain.SelectedWheel
import com.tire.calc.smart.models.domain.Wheel
import com.tire.calc.smart.models.domain.WheelSize
import com.tire.calc.smart.models.domain.SizeType
import com.tire.calc.smart.repositories.SavedSizeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class WheelSizeViewModel(
    private val savedSizeRepository: SavedSizeRepository,
) : ViewModel() {
    private val _wheelSize: MutableLiveData<WheelSize> = MutableLiveData<WheelSize>()
    val wheelSize: LiveData<WheelSize> = _wheelSize

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _saved: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved

    fun getWheel(selectedWheel: SelectedWheel) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            savedSizeRepository
                .getSelectedSize(selectedWheel.id)
                .collect { selectedWheelSize ->
                    _wheelSize.postValue(
                        selectedWheelSize?.let {
                            WheelSize.fromEntity(it.wheelSize)
                        } ?: WheelSize.defaultReference()
                    )
                }
        }
    }

    fun setWheel(wheel: Wheel) {
        _wheelSize.postValue(WheelSize.fromEntity(wheel.size))
    }

    fun setSize(sizeType: SizeType, size: Double) {
        _wheelSize.value
            ?.let { wheelSize ->
            _wheelSize.postValue(
                when (sizeType) {
                    SizeType.RimWidth -> wheelSize.copy(rimWidth = size)
                    SizeType.RimHeight -> wheelSize.copy(rimHeight = size)
                    SizeType.RimET -> wheelSize.copy(rimEt = size)
                    SizeType.TireWidth -> wheelSize.copy(tireWidth = size)
                    SizeType.TireHeight -> wheelSize.copy(tireHeight = size)
                }
            )
        }
    }

    fun saveWheel(selectedWheel: SelectedWheel) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            _wheelSize.value?.let { wheelSize ->
                savedSizeRepository
                    .setSelectedSize(selectedWheel.id, wheelSize.toEntity())
                    .collect {
                        if (it != 0L) {
                            _saved.postValue(true)
                        }
                    }
            }
        }
    }

    fun isFavorite(wheelSize: WheelSize) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            savedSizeRepository
                .getFavorites()
                .collect { tireSizes ->
                    _isFavorite.postValue(
                        tireSizes
                            .map { it.wheelSize }
                            .contains(wheelSize.toEntity())
                    )
                }
        }
    }

    fun setFavorite(wheelSize: WheelSize) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            savedSizeRepository
                .setFavorites(wheelSize.toEntity())
                .collect { _isFavorite.postValue(it) }
        }
    }
}