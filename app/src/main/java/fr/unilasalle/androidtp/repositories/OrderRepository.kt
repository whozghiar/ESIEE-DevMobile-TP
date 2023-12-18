package fr.unilasalle.androidtp.repositories

import fr.unilasalle.androidtp.database.daos.CartDao
import fr.unilasalle.androidtp.database.daos.OrderDao
import fr.unilasalle.androidtp.model.CartWithCartItems
import fr.unilasalle.androidtp.model.Order

class OrderRepository(
    private val orderDao: OrderDao,
    private val cartDao: CartDao // Si nécessaire pour gérer les opérations liées au panier
) {
    suspend fun createOrder(order: Order) {
        orderDao.insert(order)
    }

    suspend fun findOrderById(orderId: Int): Order {
        return orderDao.getOrderById(orderId)
    }

    suspend fun deleteOrder(order: Order) {
        orderDao.delete(order)
    }

    // Si vous avez besoin de récupérer un panier avec ses articles pour un ordre spécifique
    suspend fun fetchCartWithItemsForOrder(orderId: Int): CartWithCartItems? {
        val order = findOrderById(orderId)
        return cartDao.findCartWithItems(order.cartId)
    }
}
