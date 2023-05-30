package com.tire.calc.smart.models.dao

import androidx.room.ColumnInfo

data class ManufacturerModel(
    @ColumnInfo(name = "manufacturer_name")
    val manufacturerName: String,
    @ColumnInfo(name = "model_id")
    val modelId: Long,
    @ColumnInfo(name = "model_name")
    val modelName: String,
)