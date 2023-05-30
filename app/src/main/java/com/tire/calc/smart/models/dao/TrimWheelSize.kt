package com.tire.calc.smart.models.dao

import androidx.room.ColumnInfo

data class TrimWheelSize(
    @ColumnInfo(name = "trim_name")
    val trimName: String,
    @ColumnInfo(name = "wheel_id")
    val wheelId: Long,
    @ColumnInfo(name = "wheel_size")
    val wheelSize: String,
)