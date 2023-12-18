package fr.unilasalle.androidtp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.CartItemWithProduct
import fr.unilasalle.androidtp.model.Product

@Dao
interface CartItemDao {
    @Insert
    suspend fun insert(vararg cartItem: CartItem)

    @Update
    suspend fun update(cartItem: CartItem)

    @Delete
    suspend fun delete(vararg cartItem: CartItem)


    @Transaction
    @Query("SELECT * FROM cart_item WHERE cartId = :cartId")
    suspend fun findCartItemsByCartId(cartId: Int): List<CartItemWithProduct>

    @Transaction
    @Query ("SELECT * FROM cart_item WHERE productId = :productId")
    suspend fun findCartItemByProductId(productId: Int): CartItemWithProduct

    @Transaction
    @Query("SELECT * FROM cart_item")
    suspend fun getAllCartItems(): List<CartItem>
}