package com.asafin24.verbalcounting.screen.ResultPractic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asafin24.verbalcounting.REPOSITORY
import com.asafin24.verbalcounting.db.PracticsDataBase
import com.asafin24.verbalcounting.db.repository.PracticsRealization
import com.asafin24.verbalcounting.model.PracticModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultPracticViewModel(application: Application): AndroidViewModel(application) {
    val context = application

    fun insert(practicModel: PracticModel, onSuccess:() -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.insertPractic(practicModel) {
                onSuccess()
            }
        }


    fun initDataBase(){
        val daoPractic = PracticsDataBase.getInstance(context).getPracticDao()
        REPOSITORY = PracticsRealization(daoPractic)
    }
}