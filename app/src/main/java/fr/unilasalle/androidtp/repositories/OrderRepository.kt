package fr.unilasalle.androidtp.repositories

import fr.unilasalle.androidtp.database.daos.CartDao
import fr.unilasalle.androidtp.database.daos.OrderDao
import fr.unilasalle.androidtp.model.CartWithCartItems
import fr.unilasalle.androidtp.model.Order
import fr.unilasalle.androidtp.model.OrderWithCart

class OrderRepository(
    private val orderDao: OrderDao,
    private val cartDao: CartDao // Si nécessaire pour gérer les opérations liées au panier
) {
    /**
     * Crée une commande
     * @param order
     */
    suspend fun createOrder(order: Order) {
        orderDao.insert(order)
    }

    /**
     * Récupère une commande par son ID
     * @param orderId
     * @return Order
     */
    suspend fun findOrderById(orderId: Int): Order {
        return orderDao.getOrderById(orderId)
    }

    /**
     * Supprime une commande
     * @param order
     */
    suspend fun deleteOrder(order: Order) {
        orderDao.delete(order)
    }

    /**
     *  Récupère le panier avec les items pour une commande donnée
     */
    suspend fun fetchCartWithItemsForOrder(orderId: Int): CartWithCartItems? {
        val order = findOrderById(orderId)
        return cartDao.findCartWithItems(order.cartId)
    }

    /**
     * Récupère tous les ordres
     */
    suspend fun findOrders(): List<Order> {
        return orderDao.getOrders()
    }

    /**
     * Récupère le prix total d'une commande
     */
    suspend fun getTotalPriceForOrder(orderId: Int): Double {
        return orderDao.getTotalPriceForCart(orderId)
    }

    /**
     * Récupère toutes les commandes avec le panier associé
     */
    suspend fun findOrdersWithCart(): List<OrderWithCart> {
        return orderDao.getOrdersWithCart()
    }
}
