package fr.unilasalle.androidtp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unilasalle.androidtp.beans.Product
import fr.unilasalle.androidtp.services.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel (private val retrofitService : RetrofitService) : ViewModel(){

    // LiveData for products
    private var _product = MutableLiveData<List<Product>>()
    val product : LiveData<List<Product>> = _product

    // LiveData for products filtered by category
    private var _productsByCategory = MutableLiveData<List<Product>>()
    val productsByCategory: LiveData<List<Product>> = _productsByCategory

    init{
        fetchData()
    }

    /**
     * Fetch all products
     */
    fun fetchData() {
        viewModelScope.launch(){
            _product.value = retrofitService.getProducts()
        }
    }

    /**
     * Fetch products by category
     * @param categoryName
     */
    fun fetchProductsByCategory(categoryName: String) {
        viewModelScope.launch {
            _productsByCategory.value = retrofitService.getProductsByCategory(categoryName)
        }
    }


}