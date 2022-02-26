package com.asafin24.verbalcounting.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asafin24.verbalcounting.db.dao.PracticsDao
import com.asafin24.verbalcounting.model.PracticModel

@Database(entities = [PracticModel::class], version = 1)
abstract class PracticsDataBase: RoomDatabase() {

    abstract fun getPracticDao(): PracticsDao

    companion object {
        private var database: PracticsDataBase ?= null


        @Synchronized
        fun getInstance(context: Context): PracticsDataBase {
            return if (database == null) {
                database = Room.databaseBuilder(context, PracticsDataBase::class.java, "db").build()
                database as PracticsDataBase
            } else {
                database as PracticsDataBase
            }
        }
    }



}
