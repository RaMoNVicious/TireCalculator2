package com.tire.calc.smart.repositories

import android.content.ContentValues
import android.content.Context
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tire.calc.smart.models.dao.Manufacturer
import com.tire.calc.smart.models.dao.Model
import com.tire.calc.smart.models.dao.ModelTireSize
import com.tire.calc.smart.models.dao.ModelTrim
import com.tire.calc.smart.models.dao.TireSize

@Database(
    entities = [Manufacturer::class, Model::class, ModelTrim::class, ModelTireSize::class, TireSize::class],
    version = 1
)
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
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.insert(
                            "manufacturer",
                            OnConflictStrategy.IGNORE,
                            ContentValues().apply {
                                put("name", "Acura")
                                put("logo_url", "acura.png")
                            }
                        )
                        db.insert(
                            "manufacturer",
                            OnConflictStrategy.IGNORE,
                            ContentValues().apply {
                                put("name", "Ford")
                                put("logo_url", "ford.png")
                            }
                        )

                        db.insert(
                            "model",
                            OnConflictStrategy.IGNORE,
                            ContentValues().apply {
                                put("name", "ARX")
                                put("manufacturer_id", "1")
                            }
                        )
                        db.insert(
                            "model",
                            OnConflictStrategy.IGNORE,
                            ContentValues().apply {
                                put("name", "CDX")
                                put("manufacturer_id", "1")
                            }
                        )
                        db.insert(
                            "model",
                            OnConflictStrategy.IGNORE,
                            ContentValues().apply {
                                put("name", "Integra")
                                put("manufacturer_id", "1")
                            }
                        )
                    }
                }).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}