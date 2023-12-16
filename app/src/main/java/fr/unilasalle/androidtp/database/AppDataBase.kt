package fr.unilasalle.androidtp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.unilasalle.androidtp.database.daos.CartItemDao
import fr.unilasalle.androidtp.database.daos.CategoryDao
import fr.unilasalle.androidtp.database.daos.ProductDao
import fr.unilasalle.androidtp.model.Product
import fr.unilasalle.androidtp.model.Category
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository

@Database(entities = [Product::class, Category::class, CartItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getCartItemDao(): CartItemDao

    // Singleton pour éviter d'avoir plusieurs instances de la base de données ouvertes en même temps.
    companion object {
        // Le @Volatile  indique que la valeur de la variable INSTANCE sera toujours à jour et la même pour tous les threads d'exécution.
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Si l'instance n'est pas nulle, on la retourne
            // Si elle est nulle, on la crée
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                // On retourne l'instance
                instance
            }
        }
    }
}
