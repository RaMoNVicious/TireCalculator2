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

    private val _saved: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved

    fun getWheel(wheelSelected: String) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            when (wheelSelected) {
                Constants.SELECTED_WHEEL_REFERENCE ->
                    savedSizeRepository
                        .getReferenceSize()
                        .collect { selectedWheelSize ->
                            _wheel.postValue(
                                selectedWheelSize?.let {
                                    WheelSize.fromEntity(it.wheelSize)
                                } ?: WheelSize.defaultReference()
                            )
                        }

                Constants.SELECTED_WHEEL_CANDIDATE ->
                    savedSizeRepository
                        .getCandidateSize()
                        .collect { selectedWheelSize ->
                            _wheel.postValue(
                                selectedWheelSize?.let {
                                    WheelSize.fromEntity(it.wheelSize)
                                } ?: WheelSize.defaultCandidate()
                            )
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
                            .map { it.wheelSize }
                            .contains(wheelSize.toEntity())
                    )
                }
        }
    }

    fun setSize(sizeType: String, size: Double) {
        _wheel.value
            ?.let { wheelSize ->
            _wheel.postValue(
                when (sizeType) {
                    Constants.SIZE_RIM_WIDTH -> wheelSize.copy(rimWidth = size)
                    Constants.SIZE_RIM_HEIGHT -> wheelSize.copy(rimHeight = size)
                    Constants.SIZE_RIM_ET -> wheelSize.copy(rimEt = size)
                    Constants.SIZE_TIRE_WIDTH -> wheelSize.copy(tireWidth = size)
                    Constants.SIZE_TIRE_HEIGHT -> wheelSize.copy(tireHeight = size)
                    else -> wheelSize
                }
            )

            checkIsFavorite(wheelSize)
        }
    }

    fun saveWheel(wheelSelected: String) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            _wheel.value?.let { wheelSize ->

                if (wheelSelected == Constants.SELECTED_WHEEL_REFERENCE) {
                    savedSizeRepository
                        .setReferenceSize(wheelSize.toEntity())
                        .collect { if (it != 0L) { _saved.postValue(true) } }
                } else {
                    savedSizeRepository
                        .setCandidateSize(wheelSize.toEntity())
                        .collect { if (it != 0L) { _saved.postValue(true) } }
                }
            }
        }
    }

    fun setFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            _wheel.value?.let {
                savedSizeRepository.setFavorites(it.toEntity())
                // TODO: update Fav Button state
            }
        }
    }
}