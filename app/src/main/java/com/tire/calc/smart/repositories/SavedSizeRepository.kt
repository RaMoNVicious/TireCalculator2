package com.tire.calc.smart.repositories

import com.tire.calc.smart.models.dao.TireSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SavedSizeRepository(private val savedSizeDao: SavedSizeDao) {

    suspend fun getReferenceSize(): Flow<TireSize> {
        return flow { emit(savedSizeDao.getSelectedSize(1).first()) }
    }

    suspend fun getCandidateSize(): Flow<TireSize> {
        return flow { emit(savedSizeDao.getSelectedSize(2).first()) }
    }

    suspend fun getFavorites(): Flow<List<TireSize>> {
        return flow { emit(savedSizeDao.getFavoriteSize()) }
    }
}