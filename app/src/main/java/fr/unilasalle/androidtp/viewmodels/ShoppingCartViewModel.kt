package fr.unilasalle.androidtp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.CartItemWithProduct
import fr.unilasalle.androidtp.repositories.ProductRepository
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import kotlinx.coroutines.launch

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val productRepository: ProductRepository
) : ViewModel() {


    private val _cartItemWithProducts = MutableLiveData<List<CartItemWithProduct>>()
    val cartItemWithProducts: LiveData<List<CartItemWithProduct>> = _cartItemWithProducts

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    private var currentCartId: Int? = null

    init {
        initializeCart()
    }

    fun initializeCart() {
        viewModelScope.launch {
            val currentCart = shoppingCartRepository.findCartWithoutOrder()
            currentCartId = currentCart?.id
            if (currentCartId != null) {
                Log.d("ShoppingCartViewModel", "Panier actif trouvé : $currentCartId")
                loadCartItemWithProducts()
            } else {
                Log.d("ShoppingCartViewModel", "Aucun panier actif trouvé")
            }
        }
    }

    fun loadCartItemWithProducts() {
        viewModelScope.launch {
            val items = shoppingCartRepository.findCartItemsByCartId(currentCartId!!)
            Log.d("ShoppingCartViewModel", "loadCartItemWithProducts: $items")
            _cartItemWithProducts.value = items
        }
    }
    fun addProductToCart(productId: Int, quantity: Int) {
        viewModelScope.launch {
            val cartId = currentCartId ?: shoppingCartRepository.findCartWithoutOrder().id
            val cartItem = CartItem(id = 0, cartId = cartId, productId = productId, quantity = quantity)
            shoppingCartRepository.addOrUpdateCartItem(cartItem)
            loadCartItemWithProducts()
        }
    }

    fun removeProductFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            shoppingCartRepository.deleteCartItem(cartItem)
            loadCartItemWithProducts()
        }
    }

    fun removeAllProductsFromCart() {
        viewModelScope.launch {
            shoppingCartRepository.deleteAllCartItems()
            loadCartItemWithProducts()
        }
    }
}
