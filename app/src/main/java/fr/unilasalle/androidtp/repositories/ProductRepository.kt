package fr.unilasalle.androidtp.repositories

import android.util.Log
import fr.unilasalle.androidtp.database.daos.ProductDao
import fr.unilasalle.androidtp.model.Product
import fr.unilasalle.androidtp.network.RetrofitAPI
import fr.unilasalle.androidtp.network.RetrofitService

class ProductRepository(
    private val apiService: RetrofitService,
    private val productDao: ProductDao
) {

    /* Partie API */

    /**
     * Fonction pour récupérer tous les produits depuis l'API
     * @return List<Product>
     */
    suspend fun fetchProducts(): List<Product> {
        try{
            return apiService.getProducts()
        }catch (e: Exception){
            Log.e("ProductRepository", "Error while fetching products from API")
        }
        return emptyList()
    }

    /**
     * Fonction pour récupérer un produit par son ID depuis l'API
     * @param productId
     * @return Product
     */
    suspend fun fetchProductById(productId: Int): Product? {
        try{
            return apiService.getProductById(productId)
        }catch (e: Exception){
            Log.e("ProductRepository", "Error while fetching product from API")
        }
        return null!!
    }

    /**
     * Fonction pour récupérer les produits d'une catégorie depuis l'API
     * @param categoryName
     * @return List<Product>
     */
    suspend fun fetchProductsByCategory(categoryName: String): List<Product> {
        try{
            return apiService.getProductsByCategory(categoryName)
        }
        catch (e: Exception){
            Log.e("ProductRepository", "Error while fetching products from API")
        }
        return emptyList()
    }

    /* Partie BDD */

    /**
     * Fonction pour récupérer tous les produits depuis la BDD
     * @return List<Product>
     */
    suspend fun getAllProducts(): List<Product> {
        try{
            return productDao.getAllProducts()
        }
        catch (e: Exception){
            Log.e("ProductRepository", "Error while fetching products from database")
        }
        return emptyList()
    }

    /**
     * Fonction pour récupérer un produit par son ID depuis la BDD
     * @param productId
     * @return Product
     */
    suspend fun getProductById(productId: Int): Product? {
        try{
            return productDao.getProductById(productId)
        }
        catch (e: Exception){
            Log.e("ProductRepository", "Error while fetching product from database")
        }
        return null!!
    }

    /**
     * Fonction pour insérer un produit dans la BDD
     * @param product
     */
    suspend fun insertProduct(product: Product) {
        try{
            productDao.insertAll(product)
        }catch (e: Exception){
            Log.e("ProductRepository", "Error while inserting product in database")
        }
    }

    /**
     * Fonction pour insérer plusieurs produits dans la BDD
     * @param products
     */
    suspend fun insertAllProducts(products: List<Product>) {
        try{
            productDao.insertAll(*products.toTypedArray())
        }catch (e: Exception) {
            Log.e("ProductRepository", "${e.toString()} \nError while inserting products in database")
        }
    }

    /**
     * Fonction pour supprimer tous les produits de la BDD
     */
    suspend fun deleteAllProducts() {
        try{
            productDao.deleteAll()
        }
        catch (e: Exception){
            Log.e("ProductRepository", "Error while deleting products from database")
        }

    }

    suspend fun deleteProductById(productId: Int) {
        try {
            productDao.deleteProductById(productId)
        }
        catch (e: Exception){
            Log.e("ProductRepository", "Error while deleting product from database")
        }
    }





}