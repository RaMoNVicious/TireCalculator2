package com.tire.calc.smart.repositories

import androidx.room.Dao
import com.tire.calc.smart.models.dao.Manufacturer

@Dao
class BrandModelDao {

    fun getBrandModels() : List<Manufacturer> {
        return emptyList()
    }
}