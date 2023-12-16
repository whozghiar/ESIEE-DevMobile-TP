package fr.unilasalle.androidtp.repositories

import android.util.Log
import fr.unilasalle.androidtp.database.daos.CategoryDao
import fr.unilasalle.androidtp.model.Category
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
        try{
            return apiService.getCategories()
        }
        catch (e: Exception){
            Log.e("CategoryRepository", "Error while fetching categories from API")
        }
        return emptyList()
    }


    /* Partie BDD */

    /**
     * Insertion en BDD
     */
    fun insertAllCategories(categories: List<Category>) {
        try {
            categoryDao.insertAll(*categories.toTypedArray())
        }
        catch (e: Exception){
            Log.e("CategoryRepository", "Error while inserting categories in database")
        }
    }

    /**
     * Récupère la liste des catégories depuis la BDD
     */
    fun getAllCategories(): List<Category> {
        try{
            return categoryDao.getAllCategories()
        }
        catch (e: Exception){
            Log.e("CategoryRepository", "Error while fetching categories from database")
        }
        return emptyList()
    }

    suspend fun fetchAndStoreCategories() {
        val categories = fetchCategories()
        val categoryEntities = categories.map { Category(id = 0, name = it) }
        insertAllCategories(categoryEntities)
    }







}