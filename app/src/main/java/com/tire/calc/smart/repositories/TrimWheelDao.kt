package com.tire.calc.smart.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tire.calc.smart.models.dao.TrimWheel

@Dao
interface TrimWheelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trimWheel: TrimWheel) : Long

    @Query("SELECT * FROM trim_wheel WHERE trim_wheel.wheel_id = :wheelId")
    suspend fun trimWheelReference(wheelId: Long) : List<TrimWheel>

}