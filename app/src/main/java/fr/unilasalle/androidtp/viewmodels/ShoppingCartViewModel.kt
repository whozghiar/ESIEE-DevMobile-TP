package fr.unilasalle.androidtp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import kotlinx.coroutines.launch

class ShoppingCartViewModel(private val shoppingCartRepository: ShoppingCartRepository) : ViewModel() {
    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    // LiveData for total price
    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    // LiveData for total count
    private val _totalCount = MutableLiveData<Int>()
    val totalCount: LiveData<Int> = _totalCount

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            try {
                val items = shoppingCartRepository.getAllCartItems()
                _cartItems.value = items
                calculateTotalPrice(items)
                calculateCountTotal(items)
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    private fun calculateTotalPrice(items: List<CartItem>) {
    }

    private fun calculateCountTotal(items: List<CartItem>) {
    }


    // Add methods to add items, remove items, and clear the cart as needed
}
