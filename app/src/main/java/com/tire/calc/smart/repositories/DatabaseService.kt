package com.tire.calc.smart.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tire.calc.smart.models.dao.Manufacturer
import com.tire.calc.smart.models.dao.Model

@Database(entities = [Manufacturer::class, Model::class], version = 1)
abstract class DatabaseService : RoomDatabase() {

    abstract fun manufacturerDao(): ManufacturerDao

    abstract fun modelDao(): ModelDao

    companion object {

        @Volatile
        private var INSTANCE: DatabaseService? = null

        fun getDatabase(context: Context): DatabaseService {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseService::class.java,
                    "tires_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}