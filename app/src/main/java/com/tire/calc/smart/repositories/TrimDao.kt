package com.tire.calc.smart.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tire.calc.smart.models.dao.Trim

@Dao
interface TrimDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(trim: Trim)

    @Query("SELECT * FROM trim WHERE id = :id")
    fun get(id: Long): Trim

    @Delete
    fun delete(trim: Trim)
}