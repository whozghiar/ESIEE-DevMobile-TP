package fr.unilasalle.androidtp.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull
import java.io.Serializable

/**
 * Bean représentant un produit
 * @property title
 * @property price
 * @property description
 * @property category
 * @property image
 */

@Entity(tableName = "product")
data class Product(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title") val title: String,

    @ColumnInfo(name = "price")
    @NotNull
    val price: Double,

    @ColumnInfo(name = "description") val description: String,

    @ColumnInfo(name = "category") val category: String,

    @ColumnInfo(name = "image") val image: String


) : Serializable