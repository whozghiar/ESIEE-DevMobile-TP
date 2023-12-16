package fr.unilasalle.androidtp.viewmodelsfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.unilasalle.androidtp.repositories.ProductRepository
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import fr.unilasalle.androidtp.viewmodels.ProductListViewModel
import fr.unilasalle.androidtp.viewmodels.ShoppingCartViewModel

class ShoppingCartViewModelFactory(
    private val shoppingCartRepository: ShoppingCartRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingCartViewModel(shoppingCartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}