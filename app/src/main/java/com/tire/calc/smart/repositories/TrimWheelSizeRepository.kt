package com.tire.calc.smart.repositories

import com.tire.calc.smart.models.dao.TrimWheelSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrimWheelSizeRepository(private val trimWheelSizeDao: TrimWheelSizeDao) {

    suspend fun getModelWheelSize(modelId: Long): Flow<List<TrimWheelSize>> {
        return flow { emit(trimWheelSizeDao.getTrimWheelSize(modelId)) }
    }

}