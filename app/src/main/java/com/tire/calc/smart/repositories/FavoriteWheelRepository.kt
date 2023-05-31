package com.tire.calc.smart.repositories

import com.tire.calc.smart.models.dao.FavoriteWheel
import com.tire.calc.smart.models.dao.FavoriteWheelSize
import com.tire.calc.smart.models.dao.Wheel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteWheelRepository(
    private val wheelDao: WheelDao,
    private val selectedWheelDao: SelectedWheelDao,
    private val favoriteWheelDao: FavoriteWheelDao,
    private val trimWheelDao: TrimWheelDao,
) {
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
        return selectedWheelDao.selectedWheelReference(wheel.id).isNotEmpty()
                || favoriteWheelDao.favoriteWheelReference(wheel.id).isNotEmpty()
                || trimWheelDao.trimWheelReference(wheel.id).isNotEmpty()
    }
}