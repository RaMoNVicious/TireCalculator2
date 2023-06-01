package com.tire.calc.smart.repositories

import com.tire.calc.smart.models.dao.ManufacturerModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ManufacturerModelRepository(private val manufacturerModelDao: ManufacturerModelDao) {
    suspend fun getAll(): Flow<List<ManufacturerModel>> {
        return flow { emit(manufacturerModelDao.getAll()) }
    }

    suspend fun search(text: String): Flow<List<ManufacturerModel>> {
        return flow { emit(manufacturerModelDao.search(text)) }
    }
}