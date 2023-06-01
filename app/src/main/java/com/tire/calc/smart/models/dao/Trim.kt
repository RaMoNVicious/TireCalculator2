package com.tire.calc.smart.models.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "trim",
    foreignKeys = [
        ForeignKey(
            entity = Model::class,
            parentColumns = ["id"],
            childColumns = ["model_id"],
        )
    ]
)
data class Trim(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "model_id")
    val modelId: Long,
    val name: String
)