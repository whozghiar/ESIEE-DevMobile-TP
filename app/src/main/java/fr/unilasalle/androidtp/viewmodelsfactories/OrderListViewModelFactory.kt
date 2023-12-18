package fr.unilasalle.androidtp.viewmodelsfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.unilasalle.androidtp.repositories.CategoryRepository
import fr.unilasalle.androidtp.repositories.OrderRepository
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import fr.unilasalle.androidtp.viewmodels.OrderListViewModel

class OrderListViewModelFactory(
    private val orderRepository: OrderRepository,
    private val shoppingCartRepository: ShoppingCartRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderListViewModel(orderRepository,shoppingCartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}