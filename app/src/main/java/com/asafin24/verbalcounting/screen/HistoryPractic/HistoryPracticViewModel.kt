package com.asafin24.verbalcounting.screen.HistoryPractic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.asafin24.verbalcounting.REPOSITORY
import com.asafin24.verbalcounting.db.PracticsDataBase
import com.asafin24.verbalcounting.db.dao.PracticsDao
import com.asafin24.verbalcounting.db.repository.PracticsRealization
import com.asafin24.verbalcounting.model.PracticModel

class HistoryPracticViewModel(application: Application): AndroidViewModel(application) {

    val context = application

    fun initDataBase(){
        val daoPractic = PracticsDataBase.getInstance(context).getPracticDao()
        REPOSITORY = PracticsRealization(daoPractic)
    }

    fun getAllPractics(): LiveData<List<PracticModel>> {
        return REPOSITORY.allPractics
    }

}