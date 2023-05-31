package com.tire.calc.smart.repositories

import com.tire.calc.smart.models.dao.SelectedWheel
import com.tire.calc.smart.models.dao.SelectedWheelSize
import com.tire.calc.smart.models.dao.Wheel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SelectedWheelRepository(
    private val wheelDao: WheelDao,
    private val selectedWheelDao: SelectedWheelDao,
    private val favoriteWheelDao: FavoriteWheelDao,
    private val trimWheelDao: TrimWheelDao,
) {

    suspend fun getSelectedSize(id: Long): Flow<SelectedWheelSize?> {
        return flow {
            emit(selectedWheelDao.getSelectedSize(id))
        }
    }

    suspend fun setSelectedSize(selectedId: Long, size: String): Flow<Long> {
        val selectedWheelSize = selectedWheelDao.getSelectedSize(selectedId)

        return flow {
            emit(
                selectedWheelDao
                    .insert(
                        SelectedWheel(
                            id = selectedId,
                            wheelId = wheelDao.getWheel(size)?.id
                                ?: wheelDao.insert(Wheel(size = size))
                        )
                    ).apply {
                        selectedWheelSize
                            ?.wheelId
                            ?.let { wheelId ->
                                wheelDao.getWheel(wheelId)
                                    ?.takeIf { hasReferences(it).not() }
                                    ?.let {
                                        wheelDao.delete(it)
                                    }
                            }
                    }
            )
        }
    }

    private suspend fun hasReferences(wheel: Wheel): Boolean {
        return selectedWheelDao.selectedWheelReference(wheel.id).isNotEmpty()
                || favoriteWheelDao.favoriteWheelReference(wheel.id).isNotEmpty()
                || trimWheelDao.trimWheelReference(wheel.id).isNotEmpty()
    }
}