package fr.unilasalle.androidtp

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import fr.unilasalle.androidtp.adapters.ProductAdapter
import fr.unilasalle.androidtp.databinding.ActivityMainBinding
import fr.unilasalle.androidtp.API.RetrofitAPI
import fr.unilasalle.androidtp.services.RetrofitService
import fr.unilasalle.androidtp.viewmodels.ProductViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter
    private val service = RetrofitAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = RetrofitAPI.getService()
        val productViewModel = ProductViewModel(service)


        // Mise en place du RecyclerView pour afficher les produits
        productViewModel.product.observe(this) {
            adapter = ProductAdapter(it)
            binding.listeImage.adapter = adapter
            binding.listeImage.layoutManager = LinearLayoutManager(this)
        }


        // Mise en place du Spinner pour afficher les catégories
        productViewModel.categories.observe(this) {
            Log.d("MainActivity", "Categories: $it")

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, it)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.categorySpinner.adapter = adapter
        }





    }
}