package fr.unilasalle.androidtp.repositories

import fr.unilasalle.androidtp.database.daos.CategoryDao
import fr.unilasalle.androidtp.network.RetrofitService

class CategoryRepository (
    private val apiService : RetrofitService,

    private val categoryDao : CategoryDao
){

    /* Partie API */

    /**
     * Récupère la liste des catégories depuis l'API
     */
    suspend fun fetchCategories(): List<String> {
        return apiService.getCategories()
    }

    /* Partie BDD */

    /**
     * Récupère la liste des catégories depuis la BDD
     */
    fun getAllCategories(): List<String> {
        return categoryDao.getAllCategories()
    }








}