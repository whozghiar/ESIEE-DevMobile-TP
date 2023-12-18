package fr.unilasalle.androidtp.viewmodelsfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.unilasalle.androidtp.viewmodels.CartListViewModel

class CartListViewModelFactory(
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}