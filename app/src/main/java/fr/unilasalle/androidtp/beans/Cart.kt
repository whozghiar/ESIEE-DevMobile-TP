package fr.unilasalle.androidtp.beans

data class CartItem(val product: Product, var quantity: Int){
    fun getTotalPrice(): Double {
        return product.price * quantity
    }
}