package fr.unilasalle.androidtp.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.unilasalle.androidtp.adapters.CartItemAdapter
import fr.unilasalle.androidtp.database.AppDatabase
import fr.unilasalle.androidtp.database.daos.CartDao
import fr.unilasalle.androidtp.database.daos.CartItemDao
import fr.unilasalle.androidtp.database.daos.OrderDao
import fr.unilasalle.androidtp.database.daos.ProductDao
import fr.unilasalle.androidtp.databinding.ActivityPanierBinding
import fr.unilasalle.androidtp.databinding.DialogPaymentBinding
import fr.unilasalle.androidtp.fragments.BannerFragment
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.Order
import fr.unilasalle.androidtp.network.RetrofitAPI
import fr.unilasalle.androidtp.repositories.OrderRepository
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
    private lateinit var orderDao: OrderDao
    private lateinit var cartDao : CartDao

    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var orderRepository: OrderRepository

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

        // Initialisation des DAO
        cartItemDao = AppDatabase.getDatabase(this).getCartItemDao()
        cartDao = AppDatabase.getDatabase(this).getCartDao()
        productDao = AppDatabase.getDatabase(this).getProductDao()
        orderDao = AppDatabase.getDatabase(this).getOrderDao()

        // Initialisation des repositories
        shoppingCartRepository = ShoppingCartRepository(cartItemDao,cartDao)
        productRepository = ProductRepository(api.getService(), productDao)
        orderRepository = OrderRepository(orderDao,cartDao)

        // Initialisation du ViewModel
        shoppingCartViewModelFactory = ShoppingCartViewModelFactory(shoppingCartRepository,productRepository, orderRepository)
        shoppingCartViewModel = ViewModelProvider(this@PanierActivity, shoppingCartViewModelFactory).get(
            ShoppingCartViewModel::class.java)

        // Initialisation de l'adapter et du RecyclerView
        cartItemAdapter = CartItemAdapter()
        productRecyclerView = bindingActivityPanier.cartProductsItems
        cartItemAdapter.listener = this@PanierActivity
        setupRecyclerView()

        // Observers
        observeCartItems()
        observeTotalPrice()
        observeTotalQuantity()

        // Initialisation du bouton de paiement
        setCheckoutButton()

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
            showPaymentDialog()
        }
    }

    override fun onDecreaseQuantity(cartItem: CartItem) {
        Log.d("PanierActivity", "Clic sur le bouton de suppression d'un article")
        shoppingCartViewModel.decreasedCartItemQuantity(cartItem)
    }

    override fun onRemove(cartItem: CartItem) {
        shoppingCartViewModel.deleteCartItem(cartItem)
    }

    fun showPaymentDialog() {
        shoppingCartViewModel.loadCartWithCartItems()
        shoppingCartViewModel.cartWithCartItems.observe(this, Observer { cartWithCartItems ->
            if (cartWithCartItems.cartItems.isNullOrEmpty()) {
                Toast.makeText(this, "Le panier est vide", Toast.LENGTH_LONG).show()
            } else {
                bindingDialogPayment = DialogPaymentBinding.inflate(layoutInflater)

                val dialogBuilder = AlertDialog.Builder(this)
                    .setView(bindingDialogPayment.root)
                    .setTitle("Validation de l'achat")

                val alertDialog = dialogBuilder.show()

                setBuyBtn()
            }
        })
    }

    // Fonction pour initialiser le bouton de paiement
    /**
     * Gestion du bouton de paiement
     */
    private fun setBuyBtn() {
        bindingDialogPayment.idBuyBtn.setOnClickListener {
            Log.d("PanierActivity", "Clic sur le bouton de paiement")

            // Si les champs sont vides, on affiche un message d'erreur
            if (bindingDialogPayment.editTextCardNumber.text.isNullOrEmpty() || bindingDialogPayment.editTextExpirationDate.text.isNullOrEmpty() || bindingDialogPayment.editTextCvv.text.isNullOrEmpty() || bindingDialogPayment.editTextCardHolder.text.isNullOrEmpty()){
                Log.d("PanierActivity", "Champs vides")
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
            } else {
                // Créer un order avec le panier actif
                shoppingCartViewModel.createOrder()
                Log.d("PanierActivity", "Paiement effectué")
                Toast.makeText(this, "Paiement effectué", Toast.LENGTH_LONG).show()

            }
        }
    }
}
