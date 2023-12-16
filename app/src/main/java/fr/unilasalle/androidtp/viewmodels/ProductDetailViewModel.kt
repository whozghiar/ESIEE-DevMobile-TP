package fr.unilasalle.androidtp.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unilasalle.androidtp.model.Product
import fr.unilasalle.androidtp.repositories.ProductRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _product = MutableLiveData<Product?>()
    val product: MutableLiveData<Product?> = _product

    // Call this method with the product ID when the product detail is needed
    fun loadProductDetail(productId: Int) {
        viewModelScope.launch {
            try {
                val productDetail = productRepository.fetchProductById(productId)
                _product.value = productDetail
            } catch (e: Exception) {
                // Handle the exception, e.g., by updating a LiveData that the UI can observe
            }
        }
    }
}
