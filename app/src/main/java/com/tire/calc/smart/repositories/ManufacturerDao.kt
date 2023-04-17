package com.tire.calc.smart.repositories

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tire.calc.smart.models.dao.Manufacturer

@Dao
interface ManufacturerDao {

    @Query("SELECT * from manufacturer ORDER BY name ASC")
    fun getManufacturers(): LiveData<List<Manufacturer>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(manufacturer : Manufacturer)

    @Query("DELETE FROM manufacturer WHERE id = :manufacturerId")
    suspend fun delete(manufacturerId : Long)
}