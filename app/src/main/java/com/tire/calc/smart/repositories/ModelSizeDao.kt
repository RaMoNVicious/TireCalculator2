package com.tire.calc.smart.repositories

import androidx.room.Dao
import androidx.room.Query
import com.tire.calc.smart.models.TrimSize

@Dao
interface ModelSizeDao {
    @Query(
        """SELECT
            model_trim.name AS model_trim_name, 
            tire_size.id AS tire_size_id, 
            tire_size.size AS tire_size_name
            FROM model_trim
            JOIN model_tire_size ON model_trim.id = model_tire_size.model_trim_id
            JOIN tire_size ON model_tire_size.tire_size_id = tire_size.id
            WHERE model_trim.model_id = :modelId"""
    )
    suspend fun getTrimSizes(modelId: Long): List<TrimSize>
}