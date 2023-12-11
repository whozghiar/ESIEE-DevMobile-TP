package fr.unilasalle.androidtp.Activities

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import fr.unilasalle.androidtp.adapters.ProductAdapter
import fr.unilasalle.androidtp.databinding.ActivityMainBinding
import fr.unilasalle.androidtp.API.RetrofitAPI
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

        // Mise en place du RecyclerView pour afficher tous les produits
        initRecyclerView(productViewModel, binding)

        // Mise en place du panier
        initCart (productViewModel, binding)

        // Mise en place du Spinner pour afficher les catégories de produits
        createSpinner(productViewModel, binding)

    }

    // Fonction qui permet de créer un Spinner avec les catégories de produits
    /**
     * Création du Spinner pour afficher les catégories de produits
     * @param productView : ProductViewModel (ViewModel)
     * @param binding : ActivityMainBinding (Binding)
     * @see ProductViewModel
     * @see ProductViewModel.categories
     * @see ProductViewModel.fetchCategories
     * @see ProductViewModel.fetchProductsByCategory
     */
    private fun createSpinner(productView: ProductViewModel, binding : ActivityMainBinding) {
        val spinner = binding.categorySpinner

        productView.categories.observe(this) {
            Log.d("MainActivity", "Categories: $it")

            val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, it)
            adapter.insert("Tout", 0)
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter

            binding.categorySpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    @SuppressLint("SuspiciousIndentation")
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Log.d("MainActivity", "Catégorie sélectionnée : ${it[position]}")

                        if (position == 0) productView.fetchData() // Si on sélectionne "Tout", on affiche tous les produits
                        else // Sinon, on affiche les produits de la catégorie sélectionnée
                        productView.fetchProductsByCategory(it[position])
                        productView.productsByCategory.observe(this@MainActivity) {
                            val adapter = ProductAdapter(it)
                            binding.listeImage.adapter = adapter
                            binding.listeImage.layoutManager =
                                LinearLayoutManager(this@MainActivity)
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        initRecyclerView(productView, binding)
                    }

                }
        }
    }

    // Fonction qui permet d'initialiser le RecyclerView avec tous les produits
    /**
     * Initialisation du RecyclerView avec tous les produits
     * @param productView : ProductViewModel (ViewModel)
     * @param binding : ActivityMainBinding (Binding)
     * @see ProductViewModel
     * @see ProductViewModel.product
     * @see ProductViewModel.fetchData
     */
    private fun initRecyclerView(productView: ProductViewModel, binding : ActivityMainBinding) {
        productView.fetchData()
        productView.product.observe(this) {
            val adapter = ProductAdapter(it)
            binding.listeImage.adapter = adapter
            binding.listeImage.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun initCart(productView: ProductViewModel, binding : ActivityMainBinding) {
        binding.shoppingCartIcon.setOnClickListener {
            val intent = Intent(this, PanierActivity::class.java)
            startActivity(intent)
        }
    }

}