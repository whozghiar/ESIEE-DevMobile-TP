package fr.unilasalle.androidtp.repositories

import fr.unilasalle.androidtp.database.daos.CartItemDao
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.network.RetrofitService

class ShoppingCartRepository(
    private val cartItemDao: CartItemDao
) {

    /* Partie BDD */

    /**
     * Retourne la liste de tous les produits dans le panier
     * @return List<CartItem>
     *     Liste de tous les produits dans le panier
     *     Chaque élément de la liste est un objet CartItem
     *     Un objet CartItem contient un objet Product et une quantité
     *
     */
    fun getAllCartItems(): List<CartItem> {
        return cartItemDao.getAllCartItems()
    }

    /**
     * Insère en BDD un produit dans le panier
     * @param cartItem
     *    Produit à insérer dans le panier
     *    C'est un objet CartItem qui contient un objet Product et une quantité
     */
    fun insertCartItem(cartItem: CartItem) {
        cartItemDao.insertCartItem(cartItem)
    }

    /**
     * Supprime en BDD un produit du panier
     * @param productId
     *   ID du produit à supprimer du panier
     *   C'est un entier qui représente l'ID du produit à supprimer du panier
     */
    fun deleteCartItem(productId: Int) {
        cartItemDao.deleteCartItem(productId)
    }

    /**
     * Retourne la quantité d'un produit dans le panier
     * @param productId
     * @return Int
     *    Quantité du produit dans le panier
     */
    fun getCartItemQuantity(productId: Int): Int {
        return cartItemDao.getQuantityByProductId(productId)
    }


}
