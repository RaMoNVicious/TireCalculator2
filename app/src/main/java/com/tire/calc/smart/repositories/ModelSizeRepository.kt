package com.tire.calc.smart.repositories

import com.tire.calc.smart.models.dao.TrimWheelSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ModelSizeRepository(private val modelSizeDao: ModelSizeDao) {

    suspend fun getModelSizes(modelId: Long): Flow<List<TrimWheelSize>> {
        return flow { emit(modelSizeDao.getTrimWheelSize(modelId)) }
    }

}