package fr.unilasalle.androidtp.repositories

import android.util.Log
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import fr.unilasalle.androidtp.database.daos.CartItemDao
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.network.RetrofitService

class ShoppingCartRepository(
    private val cartItemDao: CartItemDao
) {
    suspend fun insertCartItem(cartItem: CartItem) {
        try{
            cartItemDao.insert(cartItem)
            Log.d("ShoppingCartRepository", "Inserted cart item")
        }catch (e: Exception){
            Log.e("ShoppingCartRepository", "Error while inserting cart item", e)
        }
    }
    suspend fun deleteCartItem(productId: Int) {
        try{
            cartItemDao.deleteCartItem(productId)
            Log.d("ShoppingCartRepository", "Deleted cart item")
        }catch (e: Exception){
            Log.e("ShoppingCartRepository", "Error while deleting cart item", e)
        }

    }
    suspend fun insertOrUpdateCartItem(cartItem: CartItem) {
        try{
            val existingItem = cartItemDao.getCartItemByProductId(cartItem.productId)
            Log.d("ShoppingCartRepository", "Existing item: $existingItem")
            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + cartItem.quantity)
                cartItemDao.update(updatedItem)
                Log.d("ShoppingCartRepository", "Updated cart item")
            } else {
                cartItemDao.insert(cartItem)
            }
        }catch (e: Exception){
            Log.e("ShoppingCartRepository", "Error while inserting or updating cart item", e)
        }

    }


}
