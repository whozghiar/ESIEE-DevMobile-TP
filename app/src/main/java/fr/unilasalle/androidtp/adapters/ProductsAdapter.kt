package fr.unilasalle.androidtp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.unilasalle.androidtp.R
import fr.unilasalle.androidtp.beans.Product
import fr.unilasalle.androidtp.databinding.ProductItemBinding

class ProductAdapter(private val productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private lateinit var binding: ProductItemBinding

    class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with(holder.binding) {
            val product = productList[position]


            // Affectation des valeurs aux vues
            binding.textProductName.text  = product.title
            binding.textProductPrice.text = "${product.price}"

            // Chargement de l'image
            Glide.with(binding.imageProduct.context)
                .load(product.image)
                .into(binding.imageProduct)


        }
    }

    override fun getItemCount() = productList.size
}
