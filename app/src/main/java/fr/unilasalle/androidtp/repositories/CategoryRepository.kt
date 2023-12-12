package fr.unilasalle.androidtp.repositories

import fr.unilasalle.androidtp.network.RetrofitService

class CategoryRepository (
    private val apiService : RetrofitService
){

    // Function to get all categories
    suspend fun fetchCategories(): List<String> {
        return apiService.getCategories()
    }


}