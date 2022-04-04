package com.sametsisman.hedefkoy.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sametsisman.hedefkoy.model.Hedef

@Dao
interface HedefDao {

    @Insert
    suspend fun insert(hedef : Hedef) : Long

    @Query("SELECT * FROM hedef")
    suspend fun getAll() : List<Hedef>

    @Query("SELECT * FROM hedef WHERE uuid = :hedefId")
    suspend fun getHedef(hedefId : Int) : Hedef

    @Query("DELETE FROM hedef")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(hedef: Hedef)
}