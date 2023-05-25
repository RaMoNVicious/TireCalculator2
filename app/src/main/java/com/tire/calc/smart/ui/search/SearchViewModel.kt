package com.tire.calc.smart.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.calc.smart.models.SearchManufacturer
import com.tire.calc.smart.models.SearchModel
import com.tire.calc.smart.repositories.ManufacturerRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val manufacturerRepository: ManufacturerRepository
) : ViewModel() {

    private val _manufacturers: MutableLiveData<List<SearchManufacturer>> = MutableLiveData<List<SearchManufacturer>>()
    val manufacturers: LiveData<List<SearchManufacturer>> = _manufacturers

    fun getModels() =
        viewModelScope.launch {
            /*val result = withContext(Dispatchers.IO) {
                manufacturerRepository.allManufacturers
            }
            _manufacturers.postValue(result.value)*/

            _manufacturers.postValue(
                listOf(
                    SearchManufacturer(
                        manufacturerName = "Audi",
                        models = listOf(
                            SearchModel(modelName = "A1", modelId = 1),
                            SearchModel(modelName = "A2", modelId = 2),
                            SearchModel(modelName = "A3", modelId = 3),
                            SearchModel(modelName = "A4", modelId = 4),
                        ),
                    ),
                    SearchManufacturer(
                        manufacturerName = "VW",
                        models = listOf(
                            SearchModel(modelName = "Polo", modelId = 5),
                            SearchModel(modelName = "Golf", modelId = 6),
                            SearchModel(modelName = "Passat", modelId = 7),
                            SearchModel(modelName = "Arteon", modelId = 8),
                            SearchModel(modelName = "T-Cross", modelId = 9),
                            SearchModel(modelName = "T-Roc", modelId = 10),
                            SearchModel(modelName = "Tiguan", modelId = 11),
                            SearchModel(modelName = "Taureg", modelId = 12),
                        ),
                    ),
                )
            )
        }
}