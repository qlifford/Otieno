package com.example.may4.database

import android.icu.text.CaseMap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit2.http.Url

@Entity
data class ProductFromDatabase(
    @PrimaryKey(autoGenerate = true)val uid: Int?,
    @ColumnInfo(name = "title") val title: String,
  @ColumnInfo(name = "photoUrl") val photoUrl: String,
    @ColumnInfo(name = "price") val price: Double


)