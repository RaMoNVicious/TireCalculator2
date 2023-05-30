package com.tire.calc.smart.repositories

import androidx.room.Dao
import androidx.room.Query
import com.tire.calc.smart.models.dao.TrimWheelSize

@Dao
interface ModelSizeDao {
    @Query(
        """SELECT
            trim.name AS trim_name, 
            wheel.id AS wheel_id, 
            wheel.size AS wheel_size
            FROM trim
            JOIN trim_wheel ON trim.id = trim_wheel.trim_id
            JOIN wheel ON trim_wheel.wheel_id = wheel.id
            WHERE trim.model_id = :modelId"""
    )
    suspend fun getTrimWheelSize(modelId: Long): List<TrimWheelSize>
}