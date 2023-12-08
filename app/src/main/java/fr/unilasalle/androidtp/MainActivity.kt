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
    private lateinit var categories: List<String> // This should be fetched from the API or defined somewhere

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = RetrofitAPI.getService()
        val productViewModel = ProductViewModel(service)


        // Setup the recycler view
        productViewModel.product.observe(this, {
            adapter = ProductAdapter(it)
            binding.listeImage.adapter = adapter
            binding.listeImage.layoutManager = LinearLayoutManager(this)
        })



    }
}