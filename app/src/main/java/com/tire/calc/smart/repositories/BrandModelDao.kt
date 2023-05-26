package com.tire.calc.smart.repositories

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.tire.calc.smart.models.SearchManufacturer

@Dao
class BrandModelDao {

    fun getBrandModels() : List<SearchManufacturer> {


        return emptyList()
    }
}