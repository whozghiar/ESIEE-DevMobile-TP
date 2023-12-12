package fr.unilasalle.androidtp.repositories

import fr.unilasalle.androidtp.database.daos.ProductDao
import fr.unilasalle.androidtp.model.Product
import fr.unilasalle.androidtp.network.RetrofitService

class ProductRepository(
    private val apiService: RetrofitService,
    private val productDao : ProductDao
) {

    /* Partie API */

    /**
     * Fonction pour récupérer tous les produits depuis l'API
     * @return List<Product>
     */
    suspend fun fetchProducts(): List<Product> {
        return apiService.getProducts()
    }

    /**
     * Fonction pour récupérer un produit par son ID depuis l'API
     * @param productId
     * @return Product
     */
    suspend fun fetchProductById(productId: Int): Product {
        return apiService.getProductById(productId)
    }

    /**
     * Fonction pour récupérer les produits d'une catégorie depuis l'API
     * @param categoryName
     * @return List<Product>
     */
    suspend fun fetchProductsByCategory(categoryName: String): List<Product> {
        return apiService.getProductsByCategory(categoryName)
    }

    /* Partie BDD */

    /**
     * Fonction pour récupérer tous les produits depuis la BDD
     * @return List<Product>
     */
    fun getAllProducts(): List<Product> {
        return productDao.getAllProducts()
    }

    /**
     * Fonction pour récupérer un produit par son ID depuis la BDD
     * @param productId
     * @return Product
     */
    fun getProductById(productId: Int): Product {
        return productDao.getProductById(productId)
    }

    /**
     * Fonction pour insérer un produit dans la BDD
     * @param product
     */
    fun insertProduct(product: Product) {
        productDao.insertAll(product)
    }

    /**
     * Fonction pour insérer plusieurs produits dans la BDD
     * @param products
     */
    fun insertAllProducts(products: List<Product>) {
        productDao.insertAll(*products.toTypedArray())
    }

    /**
     * Fonction pour supprimer tous les produits de la BDD
     */
    fun deleteAllProducts() {
        productDao.deleteAllProducts()
    }

    fun deleteProductById(productId: Int) {
        productDao.deleteProductById(productId)
    }





}