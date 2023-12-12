package fr.unilasalle.androidtp.Activities

import BannerFragment
import android.R
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import fr.unilasalle.androidtp.adapters.ProductAdapter
import fr.unilasalle.androidtp.databinding.ActivityMainBinding
import fr.unilasalle.androidtp.network.RetrofitAPI
import fr.unilasalle.androidtp.viewmodels.ProductViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private val service = RetrofitAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = RetrofitAPI.getService()
        val productViewModel = ProductViewModel(service)

        // Mise en place du RecyclerView pour afficher tous les produits
        initRecyclerView(productViewModel, binding)

        // Bannière en haut de l'écran
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.bannerContainer.id, BannerFragment())
                .commit()
        }

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

                        if (position == 0) initRecyclerView(productView, binding)
                        else initRecyclerView(productView, binding, true, it[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Log.d("onNothingSelected","txt")
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
    private fun initRecyclerView(productView: ProductViewModel, binding : ActivityMainBinding, isFiltered : Boolean = false, categoryName : String = "Tout") {
        if (!isFiltered) productView.fetchData()
        else productView.fetchProductsByCategory(categoryName)

        productAdapter = ProductAdapter()

        binding.listeImage.adapter = productAdapter
        binding.listeImage.layoutManager = GridLayoutManager(this@MainActivity,2)

        productView.product.observe(this@MainActivity) {
            productAdapter.productList = it
        }
    }


}