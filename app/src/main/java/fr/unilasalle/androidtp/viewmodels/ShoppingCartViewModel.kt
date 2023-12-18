package fr.unilasalle.androidtp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unilasalle.androidtp.model.Cart
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.CartItemWithProduct
import fr.unilasalle.androidtp.model.CartWithCartItems
import fr.unilasalle.androidtp.model.Order
import fr.unilasalle.androidtp.repositories.OrderRepository
import fr.unilasalle.androidtp.repositories.ProductRepository
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {


    private val _cartItemWithProducts = MutableLiveData<List<CartItemWithProduct>>()
    val cartItemWithProducts: LiveData<List<CartItemWithProduct>> = _cartItemWithProducts

    private val _cartWithCartItems = MutableLiveData<CartWithCartItems>()
    val cartWithCartItems: LiveData<CartWithCartItems> = _cartWithCartItems

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    private val _totalQuantity = MutableLiveData<Int>()
    val totalQuantity: LiveData<Int> = _totalQuantity

    var currentCartId: Int? = null

    init {
        initializeCart()
    }

    fun initializeCart() {
        viewModelScope.launch {
            val currentCart = shoppingCartRepository.findCartWithoutOrder()
            currentCartId = currentCart?.id
            if (currentCartId != null) {
                Log.d("ShoppingCartViewModel", "Panier actif trouvé, id : $currentCartId")
                loadCartItemWithProducts()
            } else {
                Log.d("ShoppingCartViewModel", "Aucun panier actif trouvé")
            }
        }
    }

    /**
     * Charge le panier actif avec ses produits
     */
    fun loadCartWithCartItems() {
        viewModelScope.launch {
            val currentCart = shoppingCartRepository.findCartById(currentCartId!!)
            val cartItems = if (currentCart != null) {
                shoppingCartRepository.findCartItemsByCartId(currentCart.id)
            } else {
                listOf()
            }

            _cartWithCartItems.value = CartWithCartItems(cart = currentCart, cartItems = cartItems)
        }
    }

    /**
     *  Charge les produits du panier actif
     */
    fun loadCartItemWithProducts() {
        viewModelScope.launch {
            val items = shoppingCartRepository.findCartItemsByCartId(currentCartId!!)
            Log.d("ShoppingCartViewModel", "loadCartItemWithProducts: $items")
            _cartItemWithProducts.value = items
            computeTotalQuantity()
            computeTotalPrice()
        }
    }

    /**
     * Calcule le prix total du panier et le met à jour dans la BDD
     */
    fun computeTotalPrice() {
        viewModelScope.launch {
            val items = shoppingCartRepository.findCartItemsByCartId(currentCartId!!)
            var totalPrice = 0.0
            for (item in items) {
                val product = productRepository.fetchProductById(item.product.id)
                totalPrice += product!!.price * item.cartItem.quantity
            }
            _totalPrice.value = totalPrice
            shoppingCartRepository.updateCartPrice(currentCartId!!, totalPrice)
        }
    }

    /**
     * Calcule la quantité totale du panier et la met à jour dans la BDD
     */
    fun computeTotalQuantity() {
        viewModelScope.launch {
            val items = shoppingCartRepository.findCartItemsByCartId(currentCartId!!)
            var totalQuantity = 0
            for (item in items) {
                totalQuantity += item.cartItem.quantity
            }
            _totalQuantity.value = totalQuantity
            shoppingCartRepository.updateCartQuantity(currentCartId!!, totalQuantity)
        }
    }

    /**
     * Décrémente la quantité d'un produit dans le panier
     * @param cartItem
     */
    fun decreasedCartItemQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            shoppingCartRepository.decreaseCartItemQuantity(cartItem)
            loadCartItemWithProducts()
        }
    }

    /**
     * Supprime un produit du panier
     * @param cartItem
     */
    fun deleteCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            shoppingCartRepository.deleteCartItem(cartItem)
            loadCartItemWithProducts()
        }
    }

    /**
     * Créé une commande à partir du panier actif
     */
    fun createOrder() {
        viewModelScope.launch {
            val currentCart = shoppingCartRepository.findCartById(currentCartId!!)
            if (currentCart != null) {
                // Création d'un objet Order
                val order = Order(
                    id = 0,
                    cartId = currentCart.id,
                    date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE).format(Date())
                )
                orderRepository.createOrder(order)
                initializeCart()
            }
        }
    }

}
