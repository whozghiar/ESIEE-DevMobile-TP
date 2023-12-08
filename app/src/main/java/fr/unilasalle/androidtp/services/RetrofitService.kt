package fr.unilasalle.androidtp.services

import retrofit2.http.GET
import fr.unilasalle.androidtp.beans.Product

interface RetrofitService {
    @GET("products")
    suspend fun getProducts(): List<Product>

}