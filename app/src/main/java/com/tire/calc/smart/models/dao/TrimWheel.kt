package com.tire.calc.smart.models.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "trim_wheel",
    foreignKeys = [
        ForeignKey(
            entity = Trim::class,
            childColumns = ["trim_id"],
            parentColumns = ["id"]
        ),
        ForeignKey(
            entity = Wheel::class,
            childColumns = ["wheel_id"],
            parentColumns = ["id"]
        )
    ]
)
data class TrimWheel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "trim_id")
    val trimId: Long,
    @ColumnInfo(name = "wheel_id")
    val wheelId: Long
)