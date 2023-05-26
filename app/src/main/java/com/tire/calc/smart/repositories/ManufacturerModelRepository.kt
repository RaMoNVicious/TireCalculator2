package com.tire.calc.smart.repositories

import com.tire.calc.smart.models.ManufacturerModel

class ManufacturerModelRepository(private val manufacturerModelDao: ManufacturerModelDao) {
    fun getAll(limit: Int = 5): List<ManufacturerModel> {
        return manufacturerModelDao.getAll(limit)
    }

    fun search(text: String): List<ManufacturerModel> {
        return manufacturerModelDao.search(text)
    }
}