package fr.unilasalle.androidtp.beans

import com.google.gson.annotations.SerializedName

data class Product(

    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,


)