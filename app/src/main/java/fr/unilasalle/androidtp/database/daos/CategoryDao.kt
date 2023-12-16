package fr.unilasalle.androidtp.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.unilasalle.androidtp.model.Category


@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAllCategories(): List<Category> // Assuming categories are represented as strings

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg categories: Category)
}
