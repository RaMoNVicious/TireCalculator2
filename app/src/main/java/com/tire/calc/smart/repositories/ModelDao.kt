package com.tire.calc.smart.repositories

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tire.calc.smart.models.dao.Model

@Dao
interface ModelDao {

    @Query("SELECT * from model ORDER BY name ASC")
    fun getModels(): LiveData<List<Model>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(model : Model)

    @Query("DELETE FROM model WHERE id = :modelId")
    suspend fun delete(modelId : Int)
}