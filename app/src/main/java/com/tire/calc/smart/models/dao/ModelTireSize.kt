package com.tire.calc.smart.models.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "model_tire_size",
    foreignKeys = [
        ForeignKey(
            entity = ModelTrim::class,
            childColumns = ["model_trim_id"],
            parentColumns = ["id"]
        ),
        ForeignKey(
            entity = TireSize::class,
            childColumns = ["tire_size_id"],
            parentColumns = ["id"]
        )
    ]
)
data class ModelTireSize(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "model_trim_id")
    val modelTrimId: Long,
    @ColumnInfo(name = "tire_size_id")
    val tireSizeId: Long
)