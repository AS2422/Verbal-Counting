package com.asafin24.verbalcounting.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import com.asafin24.verbalcounting.model.PracticModel

@Dao
interface PracticsDao {

    @Query("SELECT * from practic_table")
    fun getAllPractics(): LiveData<List<PracticModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(practicModel: PracticModel)

    @Delete
    suspend fun delete(practicModel: PracticModel)

}