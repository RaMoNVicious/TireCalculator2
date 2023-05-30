package com.tire.calc.smart.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.models.dao.ManufacturerModel
import com.tire.calc.smart.models.domain.Manufacturer
import com.tire.calc.smart.models.domain.Model
import com.tire.calc.smart.repositories.ManufacturerModelRepository
import com.tire.calc.smart.repositories.SavedSizeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class SearchViewModel(
    private val manufacturerModelRepository: ManufacturerModelRepository,
    private val savedSizeRepository: SavedSizeRepository
) : ViewModel() {

    private val _manufacturers: MutableLiveData<List<Manufacturer>> =
        MutableLiveData<List<Manufacturer>>()
    val manufacturers: LiveData<List<Manufacturer>> = _manufacturers

    private fun List<ManufacturerModel>.toSearch() =
        this.groupBy { it.manufacturerName }
            .map { grouped ->
                Manufacturer(
                    name = grouped.key,
                    models = grouped.value.map {
                        Model(
                            id = it.modelId,
                            name = it.modelName
                        )
                    }
                )
            }

    init {
        getModels()
    }


    private fun getModels() {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            val items = mutableListOf<ManufacturerModel>()
            manufacturerModelRepository
                .getAll()
                .collect { items.addAll(it) }

            savedSizeRepository
                .getFavorites()
                .collect { favorites ->
                    items.addAll(
                        0,
                        favorites.map {
                            ManufacturerModel(
                                manufacturerName = "Favorites",
                                modelName = it.wheelSize,
                                modelId = it.id
                            )
                        }
                    )
                }

            _manufacturers.postValue(items.toSearch())
        }
    }

    fun search(text: String) {
        if (text.isEmpty()) {
            getModels()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                manufacturerModelRepository
                    .search(text)
                    .collect { _manufacturers.postValue(it.toSearch()) }
            }
        }
    }
}