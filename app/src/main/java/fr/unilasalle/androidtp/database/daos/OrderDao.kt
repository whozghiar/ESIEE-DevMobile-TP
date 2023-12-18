package fr.unilasalle.androidtp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import fr.unilasalle.androidtp.model.Order
import fr.unilasalle.androidtp.model.OrderWithCart

@Dao
interface OrderDao {
    @Insert
    suspend fun insert(order: Order)

    @Delete
    suspend fun delete(order: Order)
    @Transaction
    @Query("SELECT * FROM `order` WHERE id = :id")
    suspend fun getOrderById(id: Int): Order
    @Transaction
    @Query("SELECT * FROM `order`")
    suspend fun getOrders(): List<Order>

    @Transaction
    @Query("SELECT c.price AS total_price FROM `order` o JOIN cart c ON o.cartId = c.id\n WHERE o.id = :orderId")
    suspend fun getTotalPriceForCart(orderId: Int): Double

    @Transaction
    @Query("SELECT * FROM `order` o JOIN cart c ON o.cartId = c.id")
    suspend fun getOrdersWithCart() : List<OrderWithCart>


}