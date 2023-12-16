package fr.unilasalle.androidtp.Activities

//import BannerFragment
import android.R
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.unilasalle.androidtp.adapters.ProductAdapter
import fr.unilasalle.androidtp.database.AppDatabase
import fr.unilasalle.androidtp.databinding.ActivityMainBinding
import fr.unilasalle.androidtp.fragments.BannerFragment
import fr.unilasalle.androidtp.network.RetrofitAPI
import fr.unilasalle.androidtp.network.RetrofitService
import fr.unilasalle.androidtp.repositories.CategoryRepository
import fr.unilasalle.androidtp.repositories.ProductRepository
import fr.unilasalle.androidtp.viewmodels.CategoryViewModel
import fr.unilasalle.androidtp.viewmodels.ProductListViewModel
import fr.unilasalle.androidtp.viewmodels.ProductViewModel
import fr.unilasalle.androidtp.viewmodelsfactories.CategoryViewModelFactory
import fr.unilasalle.androidtp.viewmodelsfactories.ProductViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var productAdapter: ProductAdapter
    private lateinit var productListViewModel: ProductListViewModel
    private lateinit var productRecyclerView: RecyclerView


    private val api = RetrofitAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialisation du RecyclerView et de l'Adapter
        productRecyclerView = binding.listeImage

        productAdapter = ProductAdapter()
        productRecyclerView.adapter = productAdapter
        productRecyclerView.layoutManager = GridLayoutManager(this@MainActivity,2)



        val productDao = AppDatabase.getDatabase(this).getProductDao()
        val productRepository = ProductRepository(api.getService(), productDao)
        val productViewModelFactory = ProductViewModelFactory(productRepository)
        val productViewModel = ViewModelProvider(this, productViewModelFactory).get(
            ProductListViewModel::class.java) // Appel du ViewModel par la Factory.

        val categoryDao = AppDatabase.getDatabase(this).getCategoryDao()
        val categoryRepository = CategoryRepository(api.getService(),categoryDao)
        val categoryViewModelFactory = CategoryViewModelFactory(categoryRepository)
        val categoryViewModel = ViewModelProvider(this, categoryViewModelFactory).get(
            CategoryViewModel::class.java) // Appel du ViewModel par la Factory.


        // Bannière en haut de l'écran
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.bannerContainer.id, BannerFragment())
                .commit()
        }

        // Mise en place du Spinner pour afficher les catégories de produits
        createSpinner(productViewModel, categoryViewModel, binding)

        //initRecyclerView(productViewModelFactory, binding)



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
    private fun createSpinner(productViewModel: ProductListViewModel, categoryViewModel: CategoryViewModel, binding : ActivityMainBinding) {
        val spinner = binding.categorySpinner

        categoryViewModel.categories.observe(this) {
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

                        if (position == 0) initRecyclerView(productViewModel, binding)
                        else initRecyclerView(productViewModel, binding)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Log.d("onNothingSelected","txt")
                        initRecyclerView(productViewModel, binding)
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
    private fun initRecyclerView(productViewModel: ProductListViewModel, binding : ActivityMainBinding) {



        productViewModel.loadProducts()
        productViewModel.products.observe(this@MainActivity) {
            productAdapter.dataProducts = it
        }


        productAdapter = ProductAdapter()

        binding.listeImage.adapter = productAdapter
        binding.listeImage.layoutManager = GridLayoutManager(this@MainActivity,2)

        productViewModel.products.observe(this@MainActivity) {
            productAdapter.dataProducts = it
        }
    }


}