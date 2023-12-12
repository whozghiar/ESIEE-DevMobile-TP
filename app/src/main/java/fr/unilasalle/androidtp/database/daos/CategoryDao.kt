package fr.unilasalle.androidtp.database.daos

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAllCategories(): List<String> // Assuming categories are represented as strings

}