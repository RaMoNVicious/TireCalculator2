package com.tire.calc.smart.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tire.calc.smart.models.dao.FavoriteWheel
import com.tire.calc.smart.models.dao.FavoriteWheelSize

@Dao
interface FavoriteWheelDao {

    @Query("SELECT * FROM favorite_wheel WHERE favorite_wheel.id = :id")
    suspend fun getFavoriteSize(id: Long) : FavoriteWheel?

    @Query(
        """SELECT
            favorite_wheel.id AS id,
            wheel.id AS wheel_id,
            wheel.size AS wheel_size
            FROM wheel
            INNER JOIN favorite_wheel ON favorite_wheel.wheel_id = wheel.id"""
    )
    suspend fun getFavoriteSize(): List<FavoriteWheelSize>

    @Query(
        """SELECT
            favorite_wheel.id AS id,
            wheel.id AS wheel_id,
            wheel.size AS wheel_size
            FROM wheel
            INNER JOIN favorite_wheel ON favorite_wheel.wheel_id = wheel.id
            WHERE wheel.size = :size"""
    )
    suspend fun getFavoriteSize(size: String) : FavoriteWheelSize?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteSize: FavoriteWheel) : Long

    @Delete
    suspend fun delete(favoriteSize: FavoriteWheel) : Int

    @Query("SELECT * FROM favorite_wheel WHERE favorite_wheel.wheel_id = :wheelId")
    suspend fun favoriteWheelReference(wheelId: Long) : List<FavoriteWheel>
}