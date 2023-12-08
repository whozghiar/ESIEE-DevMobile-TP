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

    private var _product = MutableLiveData<List<Product>>()
    val product : LiveData<List<Product>> = _product

    init{
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(){
            _product.value = retrofitService.getProducts()
        }
    }


}