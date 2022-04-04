package com.sametsisman.hedefkoy.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sametsisman.hedefkoy.model.Hedef

@Database(entities = arrayOf(Hedef::class), version = 1)
abstract class HedefDatabase : RoomDatabase() {

    abstract fun hedefDao() : HedefDao

    companion object {

        @Volatile private var instance : HedefDatabase? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }


        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,HedefDatabase::class.java,"hedefdatabase"
        ).build()
    }
}