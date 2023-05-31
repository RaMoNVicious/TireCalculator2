package com.tire.calc.smart.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tire.calc.smart.models.dao.SelectedWheel
import com.tire.calc.smart.models.dao.SelectedWheelSize

@Dao
interface SelectedWheelDao {

    @Query(
        """SELECT
            selected_wheel.id AS id,
            wheel.id AS wheel_id,
            wheel.size AS wheel_size
            FROM wheel
            INNER JOIN selected_wheel ON selected_wheel.wheel_id = wheel.id
            WHERE selected_wheel.id = :selectedId"""
    )
    suspend fun getSelectedSize(selectedId: Long): SelectedWheelSize?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(selectedSize: SelectedWheel) : Long

    @Query("SELECT * FROM selected_wheel WHERE selected_wheel.wheel_id = :wheelId")
    suspend fun selectedWheelReference(wheelId: Long) : List<SelectedWheel>
}