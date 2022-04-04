package com.sametsisman.hedefkoy.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sametsisman.hedefkoy.model.Hedef
import com.sametsisman.hedefkoy.service.HedefDatabase
import kotlinx.coroutines.launch

class HedefViewModel(application: Application) : BaseViewModel(application) {

    val detail = MutableLiveData<Boolean>()

    fun setData(hedef: Hedef){
        launch {
            val db = HedefDatabase(getApplication()).hedefDao()
            db.insert(hedef)
        }
    }

}