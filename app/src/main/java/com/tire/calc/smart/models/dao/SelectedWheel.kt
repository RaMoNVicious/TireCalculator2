package com.tire.calc.smart.models.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "selected_wheel",
    foreignKeys = [
        ForeignKey(
            entity = Wheel::class,
            childColumns = ["wheel_id"],
            parentColumns = ["id"],
        )
    ]
)
data class SelectedWheel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "wheel_id")
    val wheelId: Long,
)