package com.sametsisman.hedefkoy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hedef(
    @ColumnInfo(name = "baslik")
    val baslik : String?,

    @ColumnInfo(name = "hedef")
    val hedef : String?,

    @ColumnInfo(name = "hafta")
    val hafta : String?,

    @ColumnInfo(name = "gun")
    val gun : String?,

    @ColumnInfo(name = "saat")
    val saat : String?,

    @ColumnInfo(name = "dakika")
    val dakika : String?,

    @ColumnInfo(name = "Time")
    val Time : Long?
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}