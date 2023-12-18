package fr.unilasalle.androidtp.model

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable



@Entity(
    tableName = "cart_item",
    foreignKeys = [
        ForeignKey(
            entity = Cart::class,
            parentColumns = ["id"],
            childColumns = ["cartId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "cartId")
    val cartId: Int, // Clé étrangère pour référencer le Cart
    @ColumnInfo(name = "productId")
    val productId: Int, // Clé étrangère pour référencer le Product
    @ColumnInfo(name = "quantity")
    var quantity: Int // Quantité du produit
) : Serializable

data class CartItemWithProduct(
    @Embedded val cartItem: CartItem,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: Product
)


data class CartWithCartItems(
    @Embedded val cart: Cart,
    @Relation(
        parentColumn = "id",
        entityColumn = "cartId",
        entity = CartItem::class
    )
    val cartItems: List<CartItemWithProduct>
)