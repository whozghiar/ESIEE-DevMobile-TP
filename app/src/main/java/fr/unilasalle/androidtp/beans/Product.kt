package fr.unilasalle.androidtp.beans

import com.google.gson.annotations.SerializedName

/**
 * Bean représentant un produit
 * @property title
 * @property price
 * @property description
 * @property category
 * @property image
 */
data class Product(

    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,


)