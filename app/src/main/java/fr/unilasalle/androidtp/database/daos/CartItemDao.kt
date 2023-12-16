package fr.unilasalle.androidtp.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.unilasalle.androidtp.model.CartItem

@Dao
interface CartItemDao {

    @Query("SELECT * FROM cart_item")
    suspend fun getAllCartItems(): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cartItem: CartItem)

    @Query("DELETE FROM cart_item WHERE product_id = :productId")
    suspend fun deleteCartItem(productId: Int)

    @Query("SELECT quantity FROM cart_item WHERE product_id = :productId")
    suspend fun getQuantityByProductId(productId: Int): Int

    @Update
    suspend fun update(cartItem: CartItem)

    @Query("SELECT * FROM cart_item WHERE product_id = :productId")
    suspend fun getCartItemByProductId(productId: Int): CartItem?


}