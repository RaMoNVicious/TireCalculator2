package com.tire.calc.smart.repositories

import androidx.room.Dao
import androidx.room.Query
import com.tire.calc.smart.models.dao.TireSize

@Dao
interface SavedSizeDao {

    @Query(
        """SELECT
            tire_size.id,
            tire_size.size
            FROM tire_size
            INNER JOIN selected_size ON selected_size.size_id = tire_size.id
            WHERE selected_size.id = :selectedId"""
    )
    suspend fun getSelectedSize(selectedId: Long): List<TireSize>

    @Query(
        """SELECT
            tire_size.id,
            tire_size.size
            FROM tire_size
            INNER JOIN favorite_size ON favorite_size.size_id = tire_size.id"""
    )
    suspend fun getFavoriteSize(): List<TireSize>
}