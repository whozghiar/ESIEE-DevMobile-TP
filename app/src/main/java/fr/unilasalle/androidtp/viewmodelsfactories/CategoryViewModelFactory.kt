package fr.unilasalle.androidtp.viewmodelsfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.unilasalle.androidtp.repositories.CategoryRepository
import fr.unilasalle.androidtp.repositories.ProductRepository
import fr.unilasalle.androidtp.viewmodels.CategoryViewModel
import fr.unilasalle.androidtp.viewmodels.ProductListViewModel

class CategoryViewModelFactory(
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
