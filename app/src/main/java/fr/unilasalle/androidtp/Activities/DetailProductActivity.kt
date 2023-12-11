package fr.unilasalle.androidtp.Activities

import BannerFragment
import android.R
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import fr.unilasalle.androidtp.beans.CartItem
import fr.unilasalle.androidtp.beans.Product
import fr.unilasalle.androidtp.beans.ShoppingCart
import fr.unilasalle.androidtp.databinding.ActivityDetailProductBinding
import fr.unilasalle.androidtp.databinding.ActivityMainBinding
import fr.unilasalle.androidtp.viewmodels.ProductViewModel

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    /**
     * Création de la vue
     * @param savedInstanceState : Bundle?
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU) // Pour le .let de l'intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bannière en haut de l'écran
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.bannerContainer.id, BannerFragment())
                .commit()
        }

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, (1..10).toList())
        binding.spinnerQuantity.adapter = adapter


        // Bouton ajouter au panier
        binding.buttonAddToCart.setOnClickListener {
            val productToAdd = intent.extras?.getSerializable("product", Product::class.java)

            ShoppingCart.addItem(CartItem(productToAdd!!, binding.spinnerQuantity.selectedItem.toString().toInt()))
            Toast.makeText(this, String.format("%d produit(s) ajouté(s) au panier",binding.spinnerQuantity.selectedItem.toString().toInt()), Toast.LENGTH_SHORT).show()
        }


        // Récupération des informations du produit transmises par l'activité précédente
        intent.extras?.let {
            product ->
            val product = product.getSerializable("product", Product::class.java)

            // Titre du produit
            binding.productTitle.text = SpannableStringBuilder().apply {
                append("Titre : ")
                setSpan(StyleSpan(Typeface.BOLD), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                append(product?.title)
            }

            // Description du produit
            binding.productDescription.text = SpannableStringBuilder().apply {
                append("Description : ")
                setSpan(StyleSpan(Typeface.BOLD), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                append(product?.description)
            }

            // Prix du produit
            binding.productPrice.text = SpannableStringBuilder().apply {
                append("Prix : ")
                setSpan(StyleSpan(Typeface.BOLD), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                append(String.format("%.2f€", product?.price))
            }

            // Catégorie du produit
            binding.productCategory.text = SpannableStringBuilder().apply {
                append("Catégorie : ")
                setSpan(StyleSpan(Typeface.BOLD), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                append(product?.category)
            }

            // Image du produit
            Glide.with(this).load(product?.image).into(binding.productImage)
        }

    }



}