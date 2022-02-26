package com.asafin24.verbalcounting.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "practic_table")
data class PracticModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo
    var date: String = "",

    @ColumnInfo
    var typeGame: String = "",

    @ColumnInfo
    var result: String = "",

//    @ColumnInfo
//    var mistakes: ArrayList<String>
    ) : Serializable