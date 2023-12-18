package fr.unilasalle.androidtp.viewmodels
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unilasalle.androidtp.database.AppDatabase
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.Product
import fr.unilasalle.androidtp.repositories.ProductRepository
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val productRepository: ProductRepository) : ViewModel() {

    /**
     * Ajoute un produit au panier
     * @param product Produit à ajouter
     * @param quantity Quantité du produit à ajouter
     */
    fun addProductToCart(product: Product, quantity: Int) {
        viewModelScope.launch {
            try {
                val cart = shoppingCartRepository.findCartWithoutOrder()
                Log.d("ProductDetailViewModel", "Cart found: $cart")
                val cartItem = CartItem(
                    id = 0,
                    cartId = cart.id,
                    productId = product.id,
                    quantity = quantity
                )
                shoppingCartRepository.addOrUpdateCartItem(cartItem)

            } catch (e: Exception) {
                Log.e("ProductDetailViewModel", "Error while adding product to cart", e)
            }
        }
    }
}
