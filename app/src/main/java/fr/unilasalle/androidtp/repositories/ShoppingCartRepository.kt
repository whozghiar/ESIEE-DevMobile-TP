package fr.unilasalle.androidtp.repositories

import android.util.Log
import fr.unilasalle.androidtp.database.daos.CartDao
import fr.unilasalle.androidtp.database.daos.CartItemDao
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.Cart
import fr.unilasalle.androidtp.model.CartItemWithProduct
import fr.unilasalle.androidtp.model.CartWithCartItems

class ShoppingCartRepository(
    private val cartItemDao: CartItemDao,
    private val cartDao: CartDao,
) {

    /* CART ITEM WITH PRODUCT */
    /**
     * Récupère tous les articles du panier par l'id du panier
     * @param cartId : Int
     * @return List<CartItemWithProduct>
     */
    suspend fun findCartItemsByCartId(cartId: Int): List<CartItemWithProduct> {
        return cartItemDao.findCartItemsByCartId(cartId)
    }

    /* CART ITEM */

    /**
     * Ajoute un article au panier ou incrémente la quantité si l'article existe déjà
     * @param cartItem : CartItem
     */
    suspend fun addOrUpdateCartItem(cartItem: CartItem) {
        val existingCartItem = cartItemDao.findCartItemByProductId(cartItem.productId)
        if (existingCartItem != null) {
            // Incrémente la quantité de l'article existant
            Log.d("ShoppingCartRepository", "Article existant trouvé : $existingCartItem")
            val updatedQuantity = existingCartItem.cartItem.quantity + cartItem.quantity
            existingCartItem.cartItem.quantity = updatedQuantity
            cartItemDao.update(existingCartItem.cartItem)
        } else {
            // Ajoute un nouvel article au panier
            Log.d("ShoppingCartRepository", "Aucun article existant trouvé : $cartItem")
            cartItemDao.insert(cartItem)
        }
    }

    suspend fun decreaseCartItemQuantity(cartItem: CartItem) {
        cartItem.quantity--
        if (cartItem.quantity == 0) {
            deleteCartItem(cartItem)
        } else {
            cartItemDao.update(cartItem)
        }
    }

    /**
     * Supprime un article du panier
     * @param cartItem : CartItem
     */
    suspend fun deleteCartItem(cartItem: CartItem) {
        cartItemDao.delete(cartItem)
    }

    /**
     * Supprime tous les articles du panier
     */
    suspend fun deleteAllCartItems() {
        cartItemDao.delete(*cartItemDao.getAllCartItems().toTypedArray())
    }

    /* CART */

    /**
     * Récupère le panier actif ou en crée un nouveau
     * @return Cart
     */
    suspend fun findCartWithoutOrder(): Cart {
        val cart = cartDao.findCartWithoutOrder()
        if (cart == null) {
            return createNewCart()
        }else{
            return cart
        }
    }

    /**
     * Récupère un panier par son id
     */
    suspend fun findCartById(cartId: Int): Cart {
        return cartDao.findCartById(cartId)
    }

    /**
     * Crée un nouveau panier
     * @return Cart
     */
    suspend fun createNewCart(): Cart {
        val newCart = Cart()
        cartDao.insert(newCart)
        return newCart
    }

    /**
     * Met à jour le prix du panier
     * @param cartId : Int
     * @param totalPrice : Double
     */
    suspend fun updateCartPrice(cartId: Int, totalPrice: Double) {
        val cart = cartDao.findCartById(cartId)
        if (cart != null){
            cart.price = totalPrice
            cartDao.update(cart)
        }
    }

    /**
     * Supprime un panier
     * @param cart : Cart
     */
    suspend fun deleteCart(cart: Cart) {
        cartDao.delete(cart)
    }

    /**
     * Supprime tous les paniers
     */
    suspend fun deleteAllCarts() {
        cartDao.delete(*cartDao.findAllCarts().toTypedArray())
    }

    /**
     * Met à jour la quantité du panier
     */
    suspend fun updateCartQuantity(currentCartId: Int, totalQuantity: Int){
        val cart = cartDao.findCartById(currentCartId)
        if (cart != null){
            cart.quantity = totalQuantity
            cartDao.update(cart)
        }
    }


}
