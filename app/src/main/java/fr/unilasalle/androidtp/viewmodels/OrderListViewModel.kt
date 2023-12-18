package fr.unilasalle.androidtp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unilasalle.androidtp.model.Order
import fr.unilasalle.androidtp.model.OrderWithCart
import fr.unilasalle.androidtp.repositories.OrderRepository
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import kotlinx.coroutines.launch

class OrderListViewModel(
    private val orderRepository: OrderRepository,
    private val shoppingCartRepository: ShoppingCartRepository
): ViewModel()  {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    private val _ordersWithCart = MutableLiveData<List<OrderWithCart>>()
    val ordersWithCart: LiveData<List<OrderWithCart>> = _ordersWithCart

    var price : Double = 0.0
    init{
        loadOrdersWithCart()
    }

    fun loadOrders() {
        viewModelScope.launch {
            try {
                val orderList = orderRepository.findOrders()
                _orders.value = orderList
            } catch (e: Exception) {
                Log.e("OrderListViewModel", "Error while loading orders", e)
            }
        }
    }

    fun loadOrdersWithCart() {
        viewModelScope.launch {
            try {
                val orderList = orderRepository.findOrdersWithCart()
                _ordersWithCart.value = orderList
            } catch (e: Exception) {
                Log.e("OrderListViewModel", "Error while loading orders", e)
            }
        }
    }




}