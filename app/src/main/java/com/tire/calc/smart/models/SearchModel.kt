package com.tire.calc.smart.models

import androidx.room.ColumnInfo

class ManufacturerModel(
    @ColumnInfo(name = "manufacturer_name")
    val manufacturerName: String,
    @ColumnInfo(name = "model_name")
    val modelName: String,
    @ColumnInfo(name = "model_id")
    val modelId: Long,
)

data class SearchManufacturer(
    val manufacturerName: String,
    val models: List<SearchModel>,
)

data class SearchModel(
    val modelId: Long,
    val modelName: String,
)

class TrimSize(
    @ColumnInfo(name = "model_trim_name")
    val trimName: String,
    @ColumnInfo(name = "tire_size_name")
    val sizeName: String,
    @ColumnInfo(name = "tire_size_id")
    val sizeId: Long,
)

data class TrimSizes(
    val trimName: String,
    val tireSize: List<TireSize>,
)

data class TireSize(
    val sizeId: Long,
    val sizeName: String,
)