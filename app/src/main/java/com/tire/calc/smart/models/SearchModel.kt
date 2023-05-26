package com.tire.calc.smart.models

import androidx.room.ColumnInfo

data class SearchManufacturer(
    val manufacturerName: String,
    val models: List<SearchModel>
)

data class SearchModel(
    val modelName: String,
    val modelId: Int
)

class ManufacturerModel(
    @ColumnInfo(name = "manufacturer_name")
    val manufacturerName: String,
    @ColumnInfo(name = "model_name")
    val modelName: String,
    @ColumnInfo(name = "model_id")
    val modelId: Long
)