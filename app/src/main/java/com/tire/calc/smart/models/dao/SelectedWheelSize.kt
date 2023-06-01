package com.tire.calc.smart.models.dao

import androidx.room.ColumnInfo

data class SelectedWheelSize(
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "wheel_id")
    val wheelId: Long,
    @ColumnInfo(name = "wheel_size")
    val wheelSize: String,
)