package fr.unilasalle.androidtp.services

import retrofit2.http.GET
import fr.unilasalle.androidtp.beans.Product
import retrofit2.http.Path

interface RetrofitService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    // Add this line for fetching products by category
    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): List<Product>
}