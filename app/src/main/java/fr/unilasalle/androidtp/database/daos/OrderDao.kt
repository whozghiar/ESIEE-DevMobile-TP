package fr.unilasalle.androidtp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.unilasalle.androidtp.model.Order

@Dao
interface OrderDao {
    @Insert
    suspend fun insert(order: Order)

    @Delete
    suspend fun delete(order: Order)

    @Query("SELECT * FROM `order` WHERE id = :id")
    suspend fun getOrderById(id: Int): Order

}