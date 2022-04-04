package com.sametsisman.hedefkoy.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sametsisman.hedefkoy.model.Hedef
import com.sametsisman.hedefkoy.service.HedefDatabase
import kotlinx.coroutines.launch

class RecyclerviewViewModel(application: Application) : BaseViewModel(application) {
    val hedefler = MutableLiveData<List<Hedef>>()

    fun getData() {
        launch {
            val hedeflerData = HedefDatabase(getApplication()).hedefDao().getAll()
            hedefler.value = hedeflerData
        }
    }

    fun deleteItem(i : Hedef){
        launch {
            val db = HedefDatabase(getApplication()).hedefDao()
            db.delete(i)
        }
    }
}