package com.tire.calc.smart.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tire.calc.smart.models.dao.FavoriteWheel
import com.tire.calc.smart.models.dao.FavoriteWheelSize
import com.tire.calc.smart.models.dao.SelectedWheel
import com.tire.calc.smart.models.dao.SelectedWheelSize
import com.tire.calc.smart.models.dao.TrimWheel

@Dao
interface SavedSizeDao {

    @Query(
        """SELECT
            selected_wheel.id AS id,
            wheel.id AS wheel_id,
            wheel.size AS wheel_size
            FROM wheel
            INNER JOIN selected_wheel ON selected_wheel.wheel_id = wheel.id
            WHERE selected_wheel.id = :selectedId"""
    )
    suspend fun getSelectedSize(selectedId: Long): SelectedWheelSize?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(selectedSize: SelectedWheel) : Long

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteSize: FavoriteWheel) : Long

    @Delete
    suspend fun delete(favoriteSize: FavoriteWheel) : Int

    @Query("SELECT * FROM favorite_wheel WHERE favorite_wheel.wheel_id = :wheelId")
    suspend fun favoriteWheelReference(wheelId: Long) : List<FavoriteWheel>

    @Query("SELECT * FROM selected_wheel WHERE selected_wheel.wheel_id = :wheelId")
    suspend fun selectedWheelReference(wheelId: Long) : List<SelectedWheel>

    @Query("SELECT * FROM trim_wheel WHERE trim_wheel.wheel_id = :wheelId")
    suspend fun trimWheelReference(wheelId: Long) : List<TrimWheel>
}