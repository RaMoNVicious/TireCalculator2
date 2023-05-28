package com.tire.calc.smart.models.dao

import androidx.room.ColumnInfo

data class TrimSize(
    @ColumnInfo(name = "model_trim_name")
    val trimName: String,
    @ColumnInfo(name = "tire_size_name")
    val sizeName: String,
    @ColumnInfo(name = "tire_size_id")
    val sizeId: Long,
)