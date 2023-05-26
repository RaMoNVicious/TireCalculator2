package com.tire.calc.smart.repositories

import androidx.room.Dao
import androidx.room.Query
import com.tire.calc.smart.models.ManufacturerModel

@Dao
interface ManufacturerModelDao {

    @Query("SELECT manufacturer.name AS manufacturer_name, model.name AS model_name, model.id AS model_id FROM model INNER JOIN manufacturer ON model.manufacturer_id = manufacturer.id ORDER BY model_name LIMIT :limit")
    suspend fun getAll(): List<ManufacturerModel>

    @Query("SELECT manufacturer.name AS manufacturer_name, model.name AS model_name, model.id AS model_id FROM model INNER JOIN manufacturer ON model.manufacturer_id = manufacturer.id WHERE manufacturer.name LIKE '%' || :text || '%'  OR model.name LIKE '%' || :text || '%'")
    suspend fun search(text: String): List<ManufacturerModel>
}