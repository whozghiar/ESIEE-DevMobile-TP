package fr.unilasalle.androidtp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unilasalle.androidtp.model.Category
import fr.unilasalle.androidtp.repositories.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    init {
        loadCategories()
        if (categories.value?.isNotEmpty() == true) { // Si la liste est vide, on la remplit
            categories.value?.let { storeCategories(it) } // On stocke les catégories dans la BDD
        }
    }

    /**
     * Récupère les catégories depuis l'API
     * Rempli la liste mutable _categories avec les catégories récupérées
     */
    private fun loadCategories() {
        viewModelScope.launch {
            try {
                _categories.value = categoryRepository.fetchCategories()
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error while fetching categories from API")
            }
        }
    }

    /**
     * Stocke les catégories dans la BDD
     * @param categories : liste des catégories à stocker
     */
    private fun storeCategories(categories: List<String>) {
        viewModelScope.launch {
            try {
                categoryRepository.insertAllCategories(categories.map { Category(id = 0, name = it) })
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error while storing categories in database")
            }
        }
    }
}
