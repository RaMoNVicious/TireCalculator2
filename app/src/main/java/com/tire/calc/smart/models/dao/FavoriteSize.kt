package com.tire.calc.smart.models.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_size",
    foreignKeys = [
        ForeignKey(
            entity = TireSize::class,
            childColumns = ["size_id"],
            parentColumns = ["id"],
        )
    ]
)
data class FavoriteSize(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "size_id")
    val sizeId: Long,
)