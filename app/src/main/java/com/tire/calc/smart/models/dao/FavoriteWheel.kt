package com.tire.calc.smart.models.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_wheel",
    foreignKeys = [
        ForeignKey(
            entity = Wheel::class,
            childColumns = ["wheel_id"],
            parentColumns = ["id"],
        )
    ]
)
data class FavoriteWheel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "wheel_id")
    val wheelId: Long,
)