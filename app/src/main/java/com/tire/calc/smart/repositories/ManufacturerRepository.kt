package com.tire.calc.smart.repositories

import androidx.lifecycle.LiveData
import com.tire.calc.smart.models.dao.Manufacturer

class ManufacturerRepository(private val manufacturerDao: ManufacturerDao) {
    val allManufacturers: LiveData<List<Manufacturer>> = manufacturerDao.getManufacturers()

    suspend fun insert(manufacturer: Manufacturer) {
        manufacturerDao.insert(manufacturer)
    }

    suspend fun delete(manufacturer: Manufacturer) {
        manufacturerDao.delete(manufacturer.id)
    }
}