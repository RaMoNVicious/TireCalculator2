package com.tire.calc.smart.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tire.calc.smart.models.dao.Model

@Dao
interface ModelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(model: Model)

    @Query("SELECT * FROM model WHERE id = :id")
    fun get(id: Long): Model

    @Delete
    fun delete(model: Model)
}