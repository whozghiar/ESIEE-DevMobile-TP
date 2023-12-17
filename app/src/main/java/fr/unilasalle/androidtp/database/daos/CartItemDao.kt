package fr.unilasalle.androidtp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.Product

@Dao
interface CartItemDao {

    @Query("SELECT * FROM cart_item")
    suspend fun getCartItems(): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cartItem: CartItem)

    @Delete
    suspend fun delete(cartItem: CartItem)

    @Query("DELETE FROM cart_item WHERE product_id = :productId")
    suspend fun deleteCartItemByProductId(productId: Int)

    @Query("SELECT quantity FROM cart_item WHERE product_id = :productId")
    suspend fun getQuantityByProductId(productId: Int): Int

    @Update
    suspend fun update(cartItem: CartItem)

    @Query("SELECT * FROM cart_item WHERE product_id = :productId")
    suspend fun getCartItemByProductId(productId: Int): CartItem?

    // Compte total des produits dans le panier
    @Query("SELECT SUM(quantity) FROM cart_item")
    suspend fun getCartSize(): Int
    @Query("SELECT SUM(product.price * cart_item.quantity) FROM product,cart_item WHERE product.id IN (SELECT product_id FROM cart_item)")
    suspend fun getCartPrice(): Double

    @Query("SELECT * FROM product WHERE product.id IN (SELECT product_id FROM cart_item)")
    suspend fun getProductsInCart(): List<Product>
    @Query("SELECT product.price * cart_item.quantity FROM product,cart_item WHERE product.id IN (SELECT product_id FROM cart_item)")
    suspend fun getCartItemPrice(): Double


}