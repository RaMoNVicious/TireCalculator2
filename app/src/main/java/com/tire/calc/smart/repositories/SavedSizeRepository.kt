package com.tire.calc.smart.repositories

import com.tire.calc.smart.models.dao.FavoriteWheel
import com.tire.calc.smart.models.dao.FavoriteWheelSize
import com.tire.calc.smart.models.dao.SelectedWheel
import com.tire.calc.smart.models.dao.SelectedWheelSize
import com.tire.calc.smart.models.dao.Wheel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SavedSizeRepository(
    private val wheelDao: WheelDao,
    private val selectedSizeDao: SelectedSizeDao,
    private val favoriteWheelDao: FavoriteWheelDao,
    private val trimWheelDao: TrimWheelDao,
) {

    suspend fun getSelectedSize(id: Long): Flow<SelectedWheelSize?> {
        return flow {
            emit(selectedSizeDao.getSelectedSize(id))
        }
    }

    suspend fun setSelectedSize(selectedId: Long, size: String): Flow<Long> {
        val selectedWheelSize = selectedSizeDao.getSelectedSize(selectedId)

        return flow {
            emit(
                selectedSizeDao
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

    suspend fun getFavorites(): Flow<List<FavoriteWheelSize>> {
        return flow { emit(favoriteWheelDao.getFavoriteSize()) }
    }

    suspend fun setFavorites(size: String): Flow<Boolean> {
        return flow {
            emit(
                favoriteWheelDao
                    .getFavoriteSize(size)
                    ?.let { favoriteWheelSize ->
                        favoriteWheelDao
                            .getFavoriteSize(favoriteWheelSize.id)
                            ?.let { favoriteWheel ->
                                favoriteWheelDao.delete(favoriteWheel)

                                wheelDao
                                    .getWheel(favoriteWheel.wheelId)
                                    ?.takeIf { hasReferences(it).not() }
                                    ?.let {
                                        wheelDao.delete(it)
                                    }
                            }
                        false
                    }
                    ?: favoriteWheelDao.insert(
                        FavoriteWheel(
                            wheelId = wheelDao.getWheel(size)?.id
                                ?: wheelDao.insert(Wheel(size = size))
                        )
                    ).let {
                        it != 0L
                    }
            )
        }
    }

    private suspend fun hasReferences(wheel: Wheel): Boolean {
        return selectedSizeDao.selectedWheelReference(wheel.id).isNotEmpty()
                || favoriteWheelDao.favoriteWheelReference(wheel.id).isNotEmpty()
                || trimWheelDao.trimWheelReference(wheel.id).isNotEmpty()
    }
}