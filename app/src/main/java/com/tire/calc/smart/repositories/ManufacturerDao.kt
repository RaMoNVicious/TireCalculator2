package com.tire.calc.smart.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tire.calc.smart.models.dao.Manufacturer

@Dao
interface ManufacturerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(manufacturer: Manufacturer)

    @Query("SELECT * FROM manufacturer WHERE id = :id")
    fun get(id: Long): Manufacturer

    @Delete
    fun delete(manufacturer: Manufacturer)
}