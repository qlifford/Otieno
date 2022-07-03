package com.example.may4

import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName("name")
    val title: String,
    @SerializedName("photo_url")
    val photoUrl:String,
    val price: Double,
    val isOnSale:Boolean
){

}