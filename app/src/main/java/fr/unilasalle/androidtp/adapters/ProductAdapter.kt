package fr.unilasalle.androidtp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.unilasalle.androidtp.R
import fr.unilasalle.androidtp.databinding.ProductItemBinding
import fr.unilasalle.androidtp.model.Product

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    /**
     * Data qui contient la liste des produits à afficher
     */
    var dataProducts: List<Product> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     *  Retourne le nombre d'éléments de la liste
     */
    override fun getItemCount(): Int = dataProducts.size

    /**
     * Création du ViewHolder pour les produits (éléments de la liste)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = dataProducts[position]
        holder.bind(product)
    }


    /**
     * ViewHolder pour les produits (éléments de la liste) : affichage des produits
     * @property binding
     *  Binding pour les produits
     *  @see ProductItemBinding
     *  @see RecyclerView.ViewHolder
     *  @see ProductViewHolder
     *  @see bind
     */
    inner class ProductViewHolder(productView : View) : RecyclerView.ViewHolder(productView) {

        // Binding avec le layout product_item.xml
        private val binding : ProductItemBinding = ProductItemBinding.bind(productView)

        /**
         * Affectation des valeurs aux vues pour chaque produit
         * @param item
         * Produit
         * @see Product
         * @see ProductItemBinding
         */
        fun bind(item: Product) {

            binding.textProductName.text = item.title
            binding.textProductPrice.text = "${item.price}"

            Glide.with(binding.imageProduct.context)
                .load(item.image)
                .into(binding.imageProduct)
        }
    }
}
