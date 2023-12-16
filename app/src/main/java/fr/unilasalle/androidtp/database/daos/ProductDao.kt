package fr.unilasalle.androidtp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.unilasalle.androidtp.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM product WHERE id = :productId")
    suspend fun getProductById(productId: Int): Product

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg products: Product)

    @Delete
    suspend fun delete(product: Product)

    @Delete
    suspend fun deleteAll(vararg products: Product)

    @Query("DELETE FROM product WHERE id = :productId")
    suspend fun deleteProductById(productId: Int)



}