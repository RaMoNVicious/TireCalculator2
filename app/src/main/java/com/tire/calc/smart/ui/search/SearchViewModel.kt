package com.tire.calc.smart.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.models.dao.ManufacturerModel
import com.tire.calc.smart.models.domain.Manufacturer
import com.tire.calc.smart.models.domain.Model
import com.tire.calc.smart.repositories.ManufacturerModelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class SearchViewModel(
    private val manufacturerModelRepository: ManufacturerModelRepository
) : ViewModel() {

    private val _manufacturers: MutableLiveData<List<Manufacturer>> =
        MutableLiveData<List<Manufacturer>>()
    val manufacturers: LiveData<List<Manufacturer>> = _manufacturers

    private fun List<ManufacturerModel>.toSearch() =
        this.groupBy { it.manufacturerName }
            .map { grouped ->
                Manufacturer(
                    manufacturerName = grouped.key,
                    models = grouped.value.map {
                        Model(
                            modelId = it.modelId,
                            modelName = it.modelName
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
            manufacturerModelRepository
                .getAll()
                .collect { _manufacturers.postValue(it.toSearch()) }
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