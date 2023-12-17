package fr.unilasalle.androidtp.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.unilasalle.androidtp.adapters.CartAdapter
import fr.unilasalle.androidtp.adapters.CartItemAdapter
import fr.unilasalle.androidtp.database.AppDatabase
import fr.unilasalle.androidtp.model.Product
import fr.unilasalle.androidtp.databinding.ActivityPanierBinding
import fr.unilasalle.androidtp.fragments.BannerFragment
import fr.unilasalle.androidtp.network.RetrofitAPI
import fr.unilasalle.androidtp.repositories.ProductRepository
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import fr.unilasalle.androidtp.viewmodels.ShoppingCartViewModel
import fr.unilasalle.androidtp.viewmodelsfactories.ShoppingCartViewModelFactory
import java.security.Provider

class PanierActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPanierBinding
    private lateinit var cartAdapter: CartItemAdapter
    private lateinit var productRecyclerView: RecyclerView

    private lateinit var shoppingCartViewModel: ShoppingCartViewModel
    private lateinit var shoppingCartViewModelFactory: ShoppingCartViewModelFactory

    private val api = RetrofitAPI

    @RequiresApi(Build.VERSION_CODES.TIRAMISU) // Pour le .let de l'intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bannière en haut de l'écran
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.bannerContainer.id, BannerFragment())
                .commit()
        }

        val productDao = AppDatabase.getDatabase(this).getProductDao()
        val productRepository = ProductRepository(api.getService(), productDao)

        val cartItemDao = AppDatabase.getDatabase(this).getCartItemDao()
        val cartItemRepository = ShoppingCartRepository(cartItemDao)

        // Initialisation du RecyclerView et de l'Adapter
        productRecyclerView = binding.cartProductsItems
        cartAdapter = CartItemAdapter()

        shoppingCartViewModelFactory = ShoppingCartViewModelFactory(cartItemRepository)
        shoppingCartViewModel = ViewModelProvider(this, shoppingCartViewModelFactory).get(
            ShoppingCartViewModel::class.java)

        shoppingCartViewModel.cartItems.observe(this) {
            cartAdapter.list = it
        }
        shoppingCartViewModel.loadCartItems()
        shoppingCartViewModel.products.observe(this) {
            cartAdapter.products = it
        }

        binding.cartProductsItems.adapter = cartAdapter
        binding.cartProductsItems.layoutManager = LinearLayoutManager(this@PanierActivity)


    }




        /* @TODO : A décommenter pour afficher la gestion du clic sur le bouton supprimer
        val listener = object : OnItemClickListener {
            override fun onDeleteProductDelete(product: Product) {
                /*
                val item = ShoppingCart.getCartItem(product)
                ShoppingCart.removeItem(item)
                updateCart(cartAdapter)
                updateTotal(binding)

                 */
            }
        }

         */

        //cartAdapter = CartAdapter(listener)

        //binding.cartProductsItems.adapter = cartAdapter
        //binding.cartProductsItems.layoutManager = LinearLayoutManager(this@PanierActivity)

    }

    fun updateCart(cartAdapter: CartAdapter) {
        //cartAdapter.cartItems = ShoppingCart.getProducts()
    }

    /**
     * Met à jour le total de produits et le prix total
     * @see ShoppingCart.getCount
     * @see ShoppingCart.getTotalPrice
     */
    fun updateTotal(binding: ActivityPanierBinding) {
        /*
        binding.idQuantityProducts.text = ShoppingCart.getCount().toString()
        binding.tvTotalAmount.text = ShoppingCart.getTotalPrice().toString()

         */
    }

interface OnItemClickListener {
    fun onDeleteProductDelete(product: Product)
}

