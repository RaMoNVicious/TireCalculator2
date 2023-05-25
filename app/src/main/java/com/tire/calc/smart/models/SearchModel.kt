package com.tire.calc.smart.models

data class SearchManufacturer(
    val manufacturerName: String,
    val models: List<SearchModel>
)

data class SearchModel(
    val modelName: String,
    val modelId: Int
)