package com.tire.calc.smart.models.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tire_size")
data class TireSize(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val size: String
)