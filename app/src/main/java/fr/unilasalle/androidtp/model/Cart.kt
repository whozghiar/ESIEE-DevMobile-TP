package fr.unilasalle.androidtp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "price")
    var price: Double = 0.0,

    @ColumnInfo(name = "quantity")
    var quantity: Int = 0,
) : Serializable

