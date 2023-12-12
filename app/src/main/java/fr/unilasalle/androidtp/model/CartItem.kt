package fr.unilasalle.androidtp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "cart_item",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CartItem(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "product_id") val productId: Int,

    @ColumnInfo(name = "quantity") var quantity: Int

) : Serializable