package com.asafin24.verbalcounting.db.repository

import androidx.lifecycle.LiveData
import com.asafin24.verbalcounting.db.dao.PracticsDao
import com.asafin24.verbalcounting.model.PracticModel

class PracticsRealization(private val practicsDao: PracticsDao): PracticsRepository {
    override val allPractics: LiveData<List<PracticModel>>
        get() = practicsDao.getAllPractics()

    override suspend fun insertPractic(practicModel: PracticModel, onSuccess: () -> Unit) {
        practicsDao.insert(practicModel)
        onSuccess()
    }

    override suspend fun deletePractic(practicModel: PracticModel, onSuccess: () -> Unit) {
        practicsDao.delete(practicModel)
        onSuccess()
    }
}