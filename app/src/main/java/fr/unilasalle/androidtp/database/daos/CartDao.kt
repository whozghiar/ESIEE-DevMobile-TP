package fr.unilasalle.androidtp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import fr.unilasalle.androidtp.model.Cart
import fr.unilasalle.androidtp.model.CartWithCartItems

@Dao
interface CartDao {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg cart: Cart)

    @Delete
    suspend fun delete(vararg cart: Cart)

    @Update
    suspend fun update(vararg cart: Cart)

    @Transaction
    @Query("SELECT * FROM cart WHERE id = :id")
    suspend fun findCartById(id: Int): Cart

    @Transaction
    @Query("SELECT * FROM cart")
    suspend fun findAllCarts(): List<Cart>


    @Transaction
    @Query("SELECT * FROM cart WHERE id = :cartId")
    suspend fun findCartWithItems(cartId: Int): CartWithCartItems

    @Transaction
    @Query("SELECT * FROM cart WHERE id NOT IN (SELECT cartId FROM `order`)")
    suspend fun findCartWithoutOrder(): Cart
}