package com.tire.calc.smart.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tire.calc.smart.models.dao.Wheel

@Dao
interface WheelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(wheel: Wheel): Long

    @Query("SELECT * FROM wheel WHERE id = :id")
    fun getWheel(id: Long): Wheel?

    @Query("SELECT * FROM wheel WHERE size = :size")
    fun getWheel(size: String): Wheel?

    @Delete
    fun delete(wheel: Wheel) : Int

}