package fr.unilasalle.androidtp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.unilasalle.androidtp.model.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg product: Product)

    @Update (onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(vararg product: Product)

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductById(id: Int): Product

    @Query("SELECT * FROM product")
    suspend fun getAllProducts(): List<Product>



}