package fr.unilasalle.androidtp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.Product
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import kotlinx.coroutines.launch

class ShoppingCartViewModel(private val shoppingCartRepository: ShoppingCartRepository) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        loadCartItems()
    }

    fun loadCartItems() {
        viewModelScope.launch {
            try {
                val items = shoppingCartRepository.getCartItems()
                _cartItems.value = items
            } catch (e: Exception) {
                Log.e("ShoppingCartViewModel", "Error while getting cart items", e)
            }
        }
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
