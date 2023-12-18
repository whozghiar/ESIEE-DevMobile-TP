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
     * @return List<String>
     *     Liste des catégories
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
     * @param categories : List<Category>
     */
    suspend fun insertAllCategories(categories: List<Category>) {
        try {
            categoryDao.insertAll(*categories.toTypedArray())
            Log.d("CategoryRepository", "Categories inserted in database")
        }
        catch (e: Exception){
            Log.e("CategoryRepository", "${e}\nError while inserting categories in database")
        }
    }

    /**
     * Récupère la liste des catégories depuis la BDD
     * @return List<Category>
     */
    suspend fun findAllCategories(): List<Category> {
        try{
            return categoryDao.getAllCategories()
        }
        catch (e: Exception){
            Log.e("CategoryRepository", "Error while fetching categories from database")
        }
        return emptyList()
    }







}