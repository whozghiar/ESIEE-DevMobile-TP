package fr.unilasalle.androidtp.repositories

import android.util.Log
import fr.unilasalle.androidtp.database.daos.CartItemDao
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.Product

class ShoppingCartRepository(
    private val cartItemDao: CartItemDao,
) {
    suspend fun getCartItems(): List<CartItem> {
        try{
            val cartItems = cartItemDao.getCartItems()
            Log.d("ShoppingCartRepository", "Got cart items")
            return cartItems
        }catch (e: Exception){
            Log.e("ShoppingCartRepository", "Error while getting cart items", e)
            return emptyList()
        }
    }
    suspend fun insertCartItem(cartItem: CartItem) {
        try{
            cartItemDao.insert(cartItem)
            Log.d("ShoppingCartRepository", "Inserted cart item")
        }catch (e: Exception){
            Log.e("ShoppingCartRepository", "Error while inserting cart item", e)
        }
    }
    suspend fun deleteCartItemByProductId(productId: Int) {
        try{
            cartItemDao.deleteCartItemByProductId(productId)
            Log.d("ShoppingCartRepository", "Deleted cart item (using productId)")
        }catch (e: Exception){
            Log.e("ShoppingCartRepository", "Error while deleting cart item", e)
        }
    }

    suspend fun delete(cartItem : CartItem){
        try{
            cartItemDao.delete(cartItem)
            Log.d("ShoppingCartRepository", "Deleted cart item")
        }catch (e: Exception){
            Log.e("ShoppingCartRepository", "Error while deleting cart item", e)
        }
    }

    suspend fun updateCartItem(cartItem: CartItem) {
        try{
            cartItemDao.update(cartItem)
            Log.d("ShoppingCartRepository", "Updated cart item")
        }catch (e: Exception){
            Log.e("ShoppingCartRepository", "Error while updating cart item", e)
        }

    }
    suspend fun insertOrUpdateCartItem(cartItem: CartItem) {
        try{
            val existingCartItem = cartItemDao.getCartItemByProductId(cartItem.productId)
            if(existingCartItem != null){
                existingCartItem.quantity += cartItem.quantity
                cartItemDao.update(existingCartItem)
                Log.d("ShoppingCartRepository", "Updated cart item")
            }else{
                cartItemDao.insert(cartItem)
                Log.d("ShoppingCartRepository", "Inserted cart item")
            }
        }catch (e: Exception){
            Log.e("ShoppingCartRepository", "Error while inserting or updating cart item", e)
        }
    }

    suspend fun getProductsInCart(): List<Product> {
        try{
            val cartItems = cartItemDao.getProductsInCart()
            Log.d("ShoppingCartRepository", "Got products in cart")
            return cartItems
        }catch (e: Exception){
            Log.e("ShoppingCartRepository", "Error while getting cart items", e)
            return emptyList()
        }
    }


}
