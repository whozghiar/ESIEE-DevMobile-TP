package fr.unilasalle.androidtp.services

import retrofit2.http.GET
import fr.unilasalle.androidtp.beans.Product
import retrofit2.http.Path

interface RetrofitService {
    @GET("products")
    /**
     * Endpoint pour récupérer les produits
     * @return List<Product>
     *     Liste des produits
     */
    suspend fun getProducts(): List<Product>


    @GET("products/category/{categoryName}")
    /**
     * Endpoint pour récupérer les produits d'une catégorie
     * @param categoryName
     * @return List<Product>
     *     Liste des produits de la catégorie
     */
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): List<Product>

    @GET("products/categories")
    /**
     * Endpoint pour récupérer les catégories
     * @return List<String>
     *     Liste des catégories
     */
    suspend fun getCategories(): List<String>
}