package fr.unilasalle.androidtp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unilasalle.androidtp.model.Product
import fr.unilasalle.androidtp.repositories.ProductRepository
import kotlinx.coroutines.launch

class ProductListViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val productList = productRepository.fetchProducts()
                _products.value = productList
                // Insertion en BDD
                productRepository.insertAllProducts(productList)
            } catch (e: Exception) {
                Log.e("ProductListViewModel", "Error while loading products", e)
            }
        }
    }

    fun loadProductsByCategory(categoryName: String) {
        viewModelScope.launch {
            try {
                val productList = productRepository.fetchProductsByCategory(categoryName)
                _products.value = productList
            } catch (e: Exception) {
                Log.e("ProductListViewModel", "Error while loading products by category", e)
            }
        }
    }
}