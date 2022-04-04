package com.sametsisman.hedefkoy.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {
    companion object {

        private val PREFERENCES_BASLIK = "preferences_baslik"
        private val PREFERENCES_HEDEF = "preferences_hedef"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: CustomSharedPreferences? = null
        private val lock = Any()

        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(lock) {
            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context: Context) : CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }



    }

    fun savePosition(baslik : String , hedef : String) {
        sharedPreferences?.edit(commit = true){
            putString(PREFERENCES_BASLIK,baslik)
            putString(PREFERENCES_HEDEF,hedef)

        }
    }

    fun putWhereIsCome(i : Int){
        sharedPreferences?.edit(commit = true){
            putInt("WHERE",i)
        }
    }

    fun getWhereIsCome() = sharedPreferences?.getInt("WHERE",0)
    fun getBaslik() = sharedPreferences?.getString(PREFERENCES_BASLIK,"")
    fun getHedef() = sharedPreferences?.getString(PREFERENCES_HEDEF,"")



}