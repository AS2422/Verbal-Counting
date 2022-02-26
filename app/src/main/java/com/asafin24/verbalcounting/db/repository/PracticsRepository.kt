package com.asafin24.verbalcounting.db.repository

import androidx.lifecycle.LiveData
import com.asafin24.verbalcounting.model.PracticModel

interface PracticsRepository {
    val allPractics: LiveData<List<PracticModel>>
    suspend fun insertPractic(practicModel: PracticModel, onSuccess:() -> Unit)
    suspend fun deletePractic(practicModel: PracticModel, onSuccess:() -> Unit)
}