package fr.unilasalle.androidtp.beans

object ShoppingCart {
    val cartItems = mutableListOf<CartItem>()

    fun addItem(cartItem: CartItem) {
        val existingItem = cartItems.find { it.product.id == cartItem.product.id }
        if (existingItem != null) {
            existingItem.quantity += cartItem.quantity
        } else {
            cartItems.add(cartItem)
        }
    }

    fun removeItem(cartItem: CartItem) {
        cartItems.remove(cartItem)
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.getTotalPrice() }
    }

    fun getProducts(): List<Product> {
        return cartItems.map { it.product }
    }

}