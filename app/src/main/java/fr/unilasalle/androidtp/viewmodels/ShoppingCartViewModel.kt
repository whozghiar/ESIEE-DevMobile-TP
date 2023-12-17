package fr.unilasalle.androidtp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.Product
import fr.unilasalle.androidtp.repositories.ProductRepository
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import kotlinx.coroutines.launch

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    private val _totalQuantity = MutableLiveData<Int>()
    val totalQuantity: LiveData<Int> = _totalQuantity
    init {
        loadCartItems()
        loadProducts()
    }

    fun loadCartItems() {
        viewModelScope.launch {
            try {
                val items = shoppingCartRepository.getCartItems()
                _cartItems.value = items
                updateTotalPriceAndQuantity(items)
            } catch (e: Exception) {
                Log.e("ShoppingCartViewModel", "Error while getting cart items", e)
            }
        }
    }

    fun deleteCartItem(cartItem : CartItem){
        viewModelScope.launch {
            try {
                shoppingCartRepository.delete(cartItem)
                Log.d("ShoppingCartViewModel", "Deleted cart item")
                loadCartItems() // Raffraichissement de la liste des objets du panier
                loadProducts() // Raffraichissement de la liste des produits
            } catch (e: Exception) {
                Log.e("ShoppingCartViewModel", "Error while deleting cart item", e)
            }
        }
    }

    fun updateCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            try {
                shoppingCartRepository.updateCartItem(cartItem)
                Log.d("ShoppingCartViewModel", "Updated cart item")
                loadCartItems() // Raffraichissement de la liste des objets du panier
                loadProducts() // Raffraichissement de la liste des produits
            } catch (e: Exception) {
                Log.e("ShoppingCartViewModel", "Error while updating cart item", e)
            }
        }
    }

    suspend fun updateTotalPriceAndQuantity(items: List<CartItem>) {
        val total = items.sumOf { it.quantity * getProductPriceById(it.productId) }
        _totalPrice.value = total
        _totalQuantity.value = items.sumOf { it.quantity }
    }

    suspend fun getProductPriceById(productId: Int): Double {

        val product = productRepository.getProductById(productId)

        return product?.price ?: 0.0

    }


    fun loadProducts() {
        viewModelScope.launch {
            try {
                val items = shoppingCartRepository.getProductsInCart()
                _products.value = items
            } catch (e: Exception) {
                Log.e("ShoppingCartViewModel", "Error while getting cart items", e)
            }
        }
    }
}
