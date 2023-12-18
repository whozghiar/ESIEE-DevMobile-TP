package fr.unilasalle.androidtp.Activities

import fr.unilasalle.androidtp.fragments.BannerFragment
import android.R
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import fr.unilasalle.androidtp.database.AppDatabase
import fr.unilasalle.androidtp.database.daos.CartDao
import fr.unilasalle.androidtp.database.daos.CartItemDao
import fr.unilasalle.androidtp.database.daos.ProductDao
import fr.unilasalle.androidtp.model.Product
import fr.unilasalle.androidtp.databinding.ActivityDetailProductBinding
import fr.unilasalle.androidtp.network.RetrofitAPI
import fr.unilasalle.androidtp.repositories.ProductRepository
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import fr.unilasalle.androidtp.viewmodels.ProductDetailViewModel
import fr.unilasalle.androidtp.viewmodelsfactories.ProductDetailViewModelFactory

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    private lateinit var productDao: ProductDao
    private lateinit var productRepository: ProductRepository

    private lateinit var cartItemDao: CartItemDao
    private lateinit var cartDao: CartDao
    private lateinit var cartItemRepository: ShoppingCartRepository


    private lateinit var productDetailViewModelFactory: ProductDetailViewModelFactory
    private lateinit var productDetailViewModel: ProductDetailViewModel


    private val api = RetrofitAPI

    /**
     * Création de la vue
     * @param savedInstanceState : Bundle?
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU) // Pour le .let de l'intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productDao = AppDatabase.getDatabase(this).getProductDao()
        productRepository = ProductRepository(api.getService(), productDao)

        cartItemDao = AppDatabase.getDatabase(this).getCartItemDao()
        cartDao = AppDatabase.getDatabase(this).getCartDao()
        cartItemRepository = ShoppingCartRepository(cartItemDao, cartDao)

        productDetailViewModelFactory = ProductDetailViewModelFactory(cartItemRepository, productRepository)
        productDetailViewModel = ViewModelProvider(this, productDetailViewModelFactory).get(
            ProductDetailViewModel::class.java)

        // Bannière en haut de l'écran
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.bannerContainer.id, BannerFragment())
                .commit()
        }

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, (1..10).toList())
        binding.spinnerQuantity.adapter = adapter

        intent.extras?.let {
            products ->
            val product = products.getSerializable("product", Product::class.java)

            // Titre du produit
            binding.productTitle.append(product?.title)


            // Description du produit
            binding.productDescription.append(product?.description)

            // Prix du produit
            binding.productPrice.append(String.format("%.2f €", product?.price))

            // Catégorie du produit
            binding.productCategory.append(product?.category)

            // Image du produit
            Glide.with(this).load(product?.image).into(binding.productImage)
        }

        // Bouton pour ajouter le produit au panier
        binding.buttonAddToCart.setOnClickListener {
            intent.extras?.let {
                products ->
                val product = products.getSerializable("product", Product::class.java)

                // Récupération de la quantité
                val quantity = binding.spinnerQuantity.selectedItem.toString().toInt()

                // Si le produit n'est pas null, on l'ajoute au panier
                if (product != null) {
                    productDetailViewModel.addProductToCart(product, quantity)
                }else{ // Sinon, on affiche un message d'erreur
                    Log.e("DetailProductActivity", "Product is null")
                }

                // Ajout du produit au panier
                Log.d("DetailProductActivity", "Ajout de $quantity ${product?.title} au panier\n" +
                        "Product: $product\n" )

                // Message de confirmation
                Toast.makeText(this, "$quantity Produit ajouté au panier", Toast.LENGTH_SHORT).show()
            }
        }

    }



}