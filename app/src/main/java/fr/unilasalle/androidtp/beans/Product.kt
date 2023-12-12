package fr.unilasalle.androidtp.beans


import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Bean représentant un produit
 * @property title
 * @property price
 * @property description
 * @property category
 * @property image
 */

data class Product(

    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,


) : Serializable