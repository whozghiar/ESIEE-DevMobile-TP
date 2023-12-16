package fr.unilasalle.androidtp.viewmodels

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
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                categoryRepository.fetchAndStoreCategories()
            } catch (e: Exception) {
                // Handle the exception, e.g., by updating a LiveData that the UI can observe
            }
        }
    }
}
