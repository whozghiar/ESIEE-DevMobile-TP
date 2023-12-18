package fr.unilasalle.androidtp.repositories

import fr.unilasalle.androidtp.database.daos.CartDao
import fr.unilasalle.androidtp.database.daos.CartItemDao
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.Cart
import fr.unilasalle.androidtp.model.CartItemWithProduct
import fr.unilasalle.androidtp.model.CartWithCartItems

class ShoppingCartRepository(
    private val cartItemDao: CartItemDao,
    private val cartDao: CartDao
) {



    /* CART WITH CART ITEMS */
    suspend fun getCartWithItems(cartId: Int): CartWithCartItems {
        val cart = cartDao.findCartById(cartId)
        val cartItemsWithProducts = findCartItemsByCartId(cartId)
        val cartItems = cartItemsWithProducts.map { it.cartItem }
        return CartWithCartItems(cart, cartItems)
    }
    suspend fun getAllCartsWithItems(): List<CartWithCartItems> {
        val carts = cartDao.findAllCarts()
        val cartItems = cartItemDao.getAllCartItems()
        val cartWithCartItems = mutableListOf<CartWithCartItems>()
        for (cart in carts) {
            val cartItem = cartItems.filter { it.cartId == cart.id }
            cartWithCartItems.add(CartWithCartItems(cart, cartItem))
        }
        return cartWithCartItems
    }

    /* CART ITEM WITH PRODUCT */
    suspend fun findCartItemsByCartId(cartId: Int): List<CartItemWithProduct> {
        return cartItemDao.findCartItemsByCartId(cartId)
    }

    /* CART ITEM */
    suspend fun addOrUpdateCartItem(cartItem: CartItem) {
        val existingCartItem = cartItemDao.findCartItemByProductId(cartItem.productId)
        if (existingCartItem != null) {
            // Incrémente la quantité de l'article existant
            val updatedQuantity = existingCartItem.cartItem.quantity + cartItem.quantity
            existingCartItem.cartItem.quantity = updatedQuantity
            cartItemDao.update(existingCartItem.cartItem)
        } else {
            cartItemDao.insert(cartItem)
        }
    }
    suspend fun deleteCartItem(cartItem: CartItem) {
        cartItemDao.delete(cartItem)
    }

    suspend fun deleteAllCartItems() {
        cartItemDao.delete(*cartItemDao.getAllCartItems().toTypedArray())
    }

    /* CART */

    suspend fun findCartWithoutOrder(): Cart {
        val cart = cartDao.findCartWithoutOrder()
        if (cart == null) {
            return createNewCart()
        }else{
            return cart
        }
    }

    suspend fun createNewCart(): Cart {
        val newCart = Cart()
        cartDao.insert(newCart)
        return newCart
    }
    suspend fun deleteCart(cart: Cart) {
        cartDao.delete(cart)
    }

    suspend fun deleteAllCarts() {
        cartDao.delete(*cartDao.findAllCarts().toTypedArray())
    }


}
