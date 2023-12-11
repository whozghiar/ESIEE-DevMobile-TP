package fr.unilasalle.androidtp.beans

object ShoppingCart {

    val cartItems = mutableListOf<CartItem>()

    /**
     * Ajoute un produit au panier
     * @param cartItem
     */
    fun addItem(cartItem: CartItem) {
        val existingItem =
            cartItems.find { it.product.id == cartItem.product.id } // On cherche si le produit est déjà dans le panier

        if (existingItem != null) { // Si le produit est déjà dans le panier on incrémente la quantité
            existingItem.quantity += cartItem.quantity
        } else { // Sinon on l'ajoute au panier
            cartItems.add(cartItem)
        }
    }

    /**
     * Supprime un produit du panier
     */
    fun removeItem(cartItem: CartItem) {
        cartItems.remove(cartItem)
    }

    /**
     * Retourne la quantité d'un produit dans le panier
     * @param product
     * @return Int
     * @see Product
     */
    fun getQuantity(product: Product): Int {
        val existingItem = cartItems.find { it.product.id == product.id }
        return existingItem?.quantity ?: 0
    }

    /**
     * Retourne le prix total du panier
     * @return Double
     * @see CartItem
     */
    fun getProducts(): List<Product> {
        return cartItems.map { it.product }
    }

    fun getCount(): Int {
        var count = 0
        cartItems.map { count += it.quantity }
        return count
    }
    fun getTotalPrice(): Double {
        var price = 0.0
        cartItems.map { price += it.quantity * it.product.price }
        return price
    }


}