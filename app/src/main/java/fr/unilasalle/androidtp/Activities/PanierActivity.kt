package fr.unilasalle.androidtp.Activities

import BannerFragment
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import fr.unilasalle.androidtp.adapters.CartAdapter
import fr.unilasalle.androidtp.adapters.ProductAdapter
import fr.unilasalle.androidtp.beans.ShoppingCart
import fr.unilasalle.androidtp.databinding.ActivityPanierBinding

class PanierActivity : AppCompatActivity() {

    lateinit var binding: ActivityPanierBinding
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

        CartAdapter(ShoppingCart.getProducts()).apply {
            binding.cartProductsItems.adapter = this
            binding.cartProductsItems.layoutManager = LinearLayoutManager(this@PanierActivity)
        }

        // Mise à jour du total de produits et du prix total
        updateTotal(binding)

    }


    // Fonction mettre à jour le total de produits et le prix total
    /**
     * Met à jour le total de produits et le prix total
     * @see ShoppingCart.getCount
     * @see ShoppingCart.getTotalPrice
     */
    fun updateTotal(binding : ActivityPanierBinding) {
        binding.idQuantityProducts.text = ShoppingCart.getCount().toString()
        binding.tvTotalAmount.text = ShoppingCart.getTotalPrice().toString()
    }
}