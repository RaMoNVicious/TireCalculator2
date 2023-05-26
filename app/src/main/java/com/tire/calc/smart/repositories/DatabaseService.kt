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

    abstract fun manufacturerModelDao(): ManufacturerModelDao

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
                        db.insert(
                            "model",
                            OnConflictStrategy.IGNORE,
                            ContentValues().apply {
                                put("name", "Fiesta")
                                put("manufacturer_id", "2")
                            }
                        )
                        db.insert(
                            "model",
                            OnConflictStrategy.IGNORE,
                            ContentValues().apply {
                                put("name", "Focus")
                                put("manufacturer_id", "2")
                            }
                        )

                        db.insert(
                            "model_trim",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("name", "SE")
                                put("model_id", "1")
                            }
                        )
                        db.insert(
                            "model_trim",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("name", "SX")
                                put("model_id", "2")
                            }
                        )
                        db.insert(
                            "model_trim",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("name", "Type-R")
                                put("model_id", "3")
                            }
                        )
                        db.insert(
                            "model_trim",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("name", "ST")
                                put("model_id", "4")
                            }
                        )
                        db.insert(
                            "model_trim",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("name", "ST")
                                put("model_id", "5")
                            }
                        )
                        db.insert(
                            "model_trim",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("name", "RS")
                                put("model_id", "5")
                            }
                        )

                        db.insert(
                            "tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("size", "175/70 5.5Jx14 ET47")
                            }
                        )
                        db.insert(
                            "tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("size", "185/65 5.5Jx14 ET43")
                            }
                        )
                        db.insert(
                            "tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("size", "195/60 6.0Jx15 ET50")
                            }
                        )
                        db.insert(
                            "tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("size", "205/50 6.5Jx16 ET52")
                            }
                        )
                        db.insert(
                            "tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("size", "215/40 7.0Jx17 ET49")
                            }
                        )

                        db.insert(
                            "model_tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("model_trim_id", "1")
                                put("tire_size_id", "4")
                            }
                        )
                        db.insert(
                            "model_tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("model_trim_id", "2")
                                put("tire_size_id", "4")
                            }
                        )
                        db.insert(
                            "model_tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("model_trim_id", "2")
                                put("tire_size_id", "5")
                            }
                        )
                        db.insert(
                            "model_tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("model_trim_id", "3")
                                put("tire_size_id", "5")
                            }
                        )
                        db.insert(
                            "model_tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("model_trim_id", "4")
                                put("tire_size_id", "4")
                            }
                        )
                        db.insert(
                            "model_tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("model_trim_id", "5")
                                put("tire_size_id", "4")
                            }
                        )
                        db.insert(
                            "model_tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("model_trim_id", "5")
                                put("tire_size_id", "5")
                            }
                        )
                        db.insert(
                            "model_tire_size",
                            OnConflictStrategy.REPLACE,
                            ContentValues().apply {
                                put("model_trim_id", "6")
                                put("tire_size_id", "5")
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