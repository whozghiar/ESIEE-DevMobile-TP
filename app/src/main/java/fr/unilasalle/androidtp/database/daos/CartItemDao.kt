package fr.unilasalle.androidtp.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.unilasalle.androidtp.model.CartItem

@Dao
interface CartItemDao {

    @Query("SELECT * FROM cart_item")
    fun getAllCartItems(): List<CartItem>

    @Insert
    fun insertCartItem(cartItem: CartItem)

    @Query("DELETE FROM cart_item WHERE product_id = :productId")
    fun deleteCartItem(productId: Int)

    @Query("SELECT quantity FROM cart_item WHERE product_id = :productId")
    fun getQuantityByProductId(productId: Int): Int



}