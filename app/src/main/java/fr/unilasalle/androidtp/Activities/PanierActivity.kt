package fr.unilasalle.androidtp.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.unilasalle.androidtp.adapters.CartItemAdapter
import fr.unilasalle.androidtp.database.AppDatabase
import fr.unilasalle.androidtp.database.daos.CartDao
import fr.unilasalle.androidtp.database.daos.CartItemDao
import fr.unilasalle.androidtp.database.daos.ProductDao
import fr.unilasalle.androidtp.databinding.ActivityPanierBinding
import fr.unilasalle.androidtp.databinding.DialogPaymentBinding
import fr.unilasalle.androidtp.fragments.BannerFragment
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.network.RetrofitAPI
import fr.unilasalle.androidtp.repositories.ProductRepository
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import fr.unilasalle.androidtp.viewmodels.ShoppingCartViewModel
import fr.unilasalle.androidtp.viewmodelsfactories.ShoppingCartViewModelFactory

class PanierActivity : AppCompatActivity(), CartItemAdapter.CartItemListener {

    private lateinit var bindingActivityPanier: ActivityPanierBinding
    private lateinit var bindingDialogPayment: DialogPaymentBinding
    private lateinit var cartItemAdapter: CartItemAdapter
    private lateinit var productRecyclerView: RecyclerView

    private lateinit var cartItemDao: CartItemDao
    private lateinit var productDao: ProductDao
    private lateinit var cartDao : CartDao

    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var productRepository: ProductRepository

    private lateinit var shoppingCartViewModel: ShoppingCartViewModel
    private lateinit var shoppingCartViewModelFactory: ShoppingCartViewModelFactory

    private val api = RetrofitAPI

    @RequiresApi(Build.VERSION_CODES.TIRAMISU) // Pour le .let de l'intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivityPanier = ActivityPanierBinding.inflate(layoutInflater)
        setContentView(bindingActivityPanier.root)

        // Bannière en haut de l'écran
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(bindingActivityPanier.bannerContainer.id, BannerFragment())
                .commit()
        }

        cartItemDao = AppDatabase.getDatabase(this).getCartItemDao()
        cartDao = AppDatabase.getDatabase(this).getCartDao()
        productDao = AppDatabase.getDatabase(this).getProductDao()

        shoppingCartRepository = ShoppingCartRepository(cartItemDao,cartDao)
        productRepository = ProductRepository(api.getService(), productDao)

        shoppingCartViewModelFactory = ShoppingCartViewModelFactory(shoppingCartRepository,productRepository)
        shoppingCartViewModel = ViewModelProvider(this@PanierActivity, shoppingCartViewModelFactory).get(
            ShoppingCartViewModel::class.java)

        cartItemAdapter = CartItemAdapter()
        productRecyclerView = bindingActivityPanier.cartProductsItems
        cartItemAdapter.listener = this@PanierActivity

        // Initialisation du RecyclerView
        setupRecyclerView()

        // Observers
        observeCartItems()
        observeTotalPrice()
        observeTotalQuantity()

        // @TODO : Gérer la liste

        /*
        cartItemAdapter.listener = this@PanierActivity
         */

        /*
        productRecyclerView = bindingActivityPanier.cartProductsItems
        productRecyclerView.adapter = cartItemAdapter
        productRecyclerView.layoutManager = LinearLayoutManager(this@PanierActivity)

         */

        /*
        observeViewModel()

        bindingActivityPanier.btnCheckout.setOnClickListener {
            onPayment()
        }

         */


    }

    private fun setupRecyclerView() {
        productRecyclerView.apply {
            adapter = cartItemAdapter
            layoutManager = LinearLayoutManager(this@PanierActivity)
        }
    }

    private fun observeCartItems() {
        shoppingCartViewModel.cartItemWithProducts.observe(this, Observer { items ->
            cartItemAdapter.cartItems = items
        })

    }

    private fun observeTotalPrice() {
        shoppingCartViewModel.totalPrice.observe(this, Observer { totalPrice ->
            bindingActivityPanier.idTotalAmount.text = "$totalPrice €"
        })
    }

    private fun observeTotalQuantity() {
        shoppingCartViewModel.totalQuantity.observe(this, Observer { totalQuantity ->
            bindingActivityPanier.idQuantityProducts.text = "$totalQuantity"
        })
    }

    /**
     * Gestion du bouton de paiement
     */
    private fun setCheckoutButton() {
        bindingActivityPanier.btnCheckout.setOnClickListener {
            Log.d("PanierActivity", "Clic sur le bouton de paiement")
            //onPayment()
        }
    }

    override fun onDecreaseQuantity(cartItem: CartItem) {
        Log.d("PanierActivity", "Clic sur le bouton de suppression d'un article")
        shoppingCartViewModel.decreasedCartItemQUantity(cartItem)
    }

    override fun onRemove(cartItem: CartItem) {
        shoppingCartViewModel.deleteCartItem(cartItem)
    }



    /*
    override  fun onDecreaseQuantity(cartItem: CartItem) {
        shoppingCartViewModel.updateCartItem(cartItem)
    }

    override  fun onRemoveItem(cartItem: CartItem) {
        shoppingCartViewModel.deleteCartItem(cartItem)
    }

    fun onPayment() {
        showPaymentDialog()
    }
    fun showPaymentDialog() {
        if (shoppingCartViewModel.cartItems.value.isNullOrEmpty()) {
            Toast.makeText(this, "Il n'y a aucun produit dans votre panier.", Toast.LENGTH_SHORT).show()
            return
        }
        bindingDialogPayment = DialogPaymentBinding.inflate(layoutInflater)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(bindingDialogPayment.root)
            .setTitle("Validation de l'achat")

        val alertDialog = dialogBuilder.show()

    }

     */
}
