package fr.unilasalle.androidtp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.unilasalle.androidtp.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAllProducts(): List<Product>

    @Query("SELECT * FROM product WHERE id = :productId")
    fun getProductById(productId: Int): Product

    @Insert
    fun insertAll(vararg products: Product)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM product WHERE id = :productId")
    fun deleteProductById(productId: Int)

    @Query("DELETE FROM product")
    fun deleteAllProducts()

}