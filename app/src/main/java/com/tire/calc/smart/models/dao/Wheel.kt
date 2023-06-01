package com.tire.calc.smart.models.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wheel")
data class Wheel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val size: String
)