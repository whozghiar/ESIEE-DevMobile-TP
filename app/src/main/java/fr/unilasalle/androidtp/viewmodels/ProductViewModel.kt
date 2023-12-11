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

    // LiveData pour les produits
    private var _product = MutableLiveData<List<Product>>()
    val product : LiveData<List<Product>> = _product

    // LiveData pour les produits d'une catégorie
    private var _productsByCategory = MutableLiveData<List<Product>>()
    val productsByCategory: LiveData<List<Product>> = _productsByCategory

    // LiveData pour les catégories
    private var _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    /**
     * Initialisation du ViewModel : on récupère les produits et les catégories au lancement de l'application
     */
    init{
        fetchData() // Fetch tous les produits (RecyclerView)
        fetchCategories() // Fetch toutes les catégories (Spinner)
    }

    /**
     * Réalise une requête pour récupérer les produits
     */
    fun fetchData() {
        viewModelScope.launch(){
            _product.value = retrofitService.getProducts()
        }
    }

    /**
     * Réalise une requête pour récupérer les produits d'une catégorie
     * @param categoryName
     */
    fun fetchProductsByCategory(categoryName: String) {
        viewModelScope.launch {
            _productsByCategory.value = retrofitService.getProductsByCategory(categoryName)
        }
    }

    /**
     * Réalise une requête pour récupérer les catégories
     */
    fun fetchCategories() {
        viewModelScope.launch {
            _categories.value = retrofitService.getCategories()
        }
    }



}