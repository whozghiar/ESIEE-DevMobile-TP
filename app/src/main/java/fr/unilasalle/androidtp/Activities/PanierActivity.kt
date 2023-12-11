package fr.unilasalle.androidtp.Activities

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

        // Bouton retour
        binding.button.setOnClickListener(){
            finish()
        }

        CartAdapter(ShoppingCart.getProducts()).apply {
            binding.cartProductsItems.adapter = this
            binding.cartProductsItems.layoutManager = LinearLayoutManager(this@PanierActivity)
        }

        binding.totalitems.text = ShoppingCart.getCount().toString()
        binding.totalPrice.text = ShoppingCart.getTotalPrice().toString()
    }
}