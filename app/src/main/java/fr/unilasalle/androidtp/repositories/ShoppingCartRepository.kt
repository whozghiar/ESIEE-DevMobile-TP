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

    /**
     * Récupère le panier avec ses articles
     * @param cartId : Int
     */
    suspend fun getCartWithItems(cartId: Int): CartWithCartItems {
        val cart = cartDao.findCartById(cartId)
        val cartItemsWithProducts = findCartItemsByCartId(cartId)
        val cartItems = cartItemsWithProducts.map { it.cartItem }
        return CartWithCartItems(cart, cartItems)
    }

    /**
     * Récupère tous les paniers avec leurs articles
     * @return List<CartWithCartItems>
     *     Liste des paniers avec leurs articles
     */
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
            val updatedQuantity = existingCartItem.cartItem.quantity + cartItem.quantity
            existingCartItem.cartItem.quantity = updatedQuantity
            cartItemDao.update(existingCartItem.cartItem)
        } else {
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
        cart.price = totalPrice
        cartDao.update(cart)
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


}
