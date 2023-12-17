package fr.unilasalle.androidtp.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.unilasalle.androidtp.adapters.CartItemAdapter
import fr.unilasalle.androidtp.database.AppDatabase
import fr.unilasalle.androidtp.database.daos.CartItemDao
import fr.unilasalle.androidtp.database.daos.ProductDao
import fr.unilasalle.androidtp.databinding.ActivityPanierBinding
import fr.unilasalle.androidtp.fragments.BannerFragment
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.network.RetrofitAPI
import fr.unilasalle.androidtp.repositories.ProductRepository
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import fr.unilasalle.androidtp.viewmodels.ShoppingCartViewModel
import fr.unilasalle.androidtp.viewmodelsfactories.ShoppingCartViewModelFactory

class PanierActivity : AppCompatActivity(), CartItemAdapter.CartItemListener {

    private lateinit var binding: ActivityPanierBinding
    private lateinit var cartItemAdapter: CartItemAdapter
    private lateinit var productRecyclerView: RecyclerView

    private lateinit var cartItemDao: CartItemDao
    private lateinit var productDao: ProductDao

    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var productRepository: ProductRepository

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

        cartItemDao = AppDatabase.getDatabase(this).getCartItemDao()
        productDao = AppDatabase.getDatabase(this).getProductDao()

        shoppingCartRepository = ShoppingCartRepository(cartItemDao)
        productRepository = ProductRepository(api.getService(), productDao)

        shoppingCartViewModelFactory = ShoppingCartViewModelFactory(shoppingCartRepository,productRepository)
        shoppingCartViewModel = ViewModelProvider(this@PanierActivity, shoppingCartViewModelFactory).get(
            ShoppingCartViewModel::class.java)

        cartItemAdapter = CartItemAdapter()
        cartItemAdapter.listener = this@PanierActivity

        productRecyclerView = binding.cartProductsItems
        productRecyclerView.adapter = cartItemAdapter
        productRecyclerView.layoutManager = LinearLayoutManager(this@PanierActivity)

        observeViewModel()



    }

    fun observeViewModel() {

        shoppingCartViewModel.cartItems.observe(this@PanierActivity, Observer{
            cartItemAdapter.cartItems = it
        })

        shoppingCartViewModel.products.observe(this@PanierActivity, Observer { products ->
            val productMap = products.associateBy { it.id }
            cartItemAdapter.products = productMap
        })

        shoppingCartViewModel.totalPrice.observe(this@PanierActivity, Observer {
            binding.idTotalAmount.text = "$it €"
        })

        shoppingCartViewModel.totalQuantity.observe(this@PanierActivity, Observer {
            binding.idQuantityProducts.text = "$it"
        })
    }

    override  fun onDecreaseQuantity(cartItem: CartItem) {
        shoppingCartViewModel.updateCartItem(cartItem)
    }

    override  fun onRemoveItem(cartItem: CartItem) {
        shoppingCartViewModel.deleteCartItem(cartItem)
    }
}
