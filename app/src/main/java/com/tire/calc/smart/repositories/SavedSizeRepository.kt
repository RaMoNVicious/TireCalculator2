package com.tire.calc.smart.repositories

import com.tire.calc.smart.app.Constants
import com.tire.calc.smart.models.dao.FavoriteWheel
import com.tire.calc.smart.models.dao.FavoriteWheelSize
import com.tire.calc.smart.models.dao.SelectedWheel
import com.tire.calc.smart.models.dao.SelectedWheelSize
import com.tire.calc.smart.models.dao.Wheel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SavedSizeRepository(
    private val wheelDao: WheelDao,
    private val savedSizeDao: SavedSizeDao
) {

    suspend fun getReferenceSize(): Flow<SelectedWheelSize?> {
        return flow {
            emit(savedSizeDao.getSelectedSize(Constants.SAVED_WHEEL_REFERENCE_ID))
        }
    }

    suspend fun getCandidateSize(): Flow<SelectedWheelSize?> {
        return flow {
            emit(savedSizeDao.getSelectedSize(Constants.SAVED_WHEEL_CANDIDATE_ID))
        }
    }

    suspend fun setReferenceSize(size: String): Flow<Long> {
        return setSelectedSize(Constants.SAVED_WHEEL_REFERENCE_ID, size)
    }

    suspend fun setCandidateSize(size: String): Flow<Long> {
        return setSelectedSize(Constants.SAVED_WHEEL_CANDIDATE_ID, size)
    }

    private suspend fun setSelectedSize(selectedId: Long, size: String): Flow<Long> {
        val selectedWheelSize = savedSizeDao.getSelectedSize(selectedId)

        return flow {
            emit(
                savedSizeDao
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
                                    ?.takeIf {
                                        hasFavoriteReference(it).or(hasTrimReference(it)).not()
                                    }
                                    ?.let {
                                        wheelDao.delete(it)
                                    }
                            }
                    }
            )
        }
    }

    suspend fun getFavorites(): Flow<List<FavoriteWheelSize>> {
        return flow { emit(savedSizeDao.getFavoriteSize()) }
    }

    suspend fun setFavorites(size: String): Flow<Long> {
        val favoriteWheelSize = savedSizeDao.getFavoriteSize(size)

        if (favoriteWheelSize == null) {
            savedSizeDao.insert(
                FavoriteWheel(
                    wheelId = wheelDao.insert(Wheel(size = size))
                )
            )
        } else {
            // TODO: handle this
        }

        return flow { emit(0L) }
    }

    private suspend fun hasSelectedReference(wheel: Wheel): Boolean {
        return savedSizeDao.selectedWheelReference(wheel.id).isNotEmpty()
    }

    private suspend fun hasFavoriteReference(wheel: Wheel): Boolean {
        return savedSizeDao.favoriteWheelReference(wheel.id).isNotEmpty()
    }

    private suspend fun hasTrimReference(wheel: Wheel): Boolean {
        return savedSizeDao.trimWheelReference(wheel.id).isNotEmpty()
    }
}