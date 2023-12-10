package fr.unilasalle.androidtp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.unilasalle.androidtp.Activities.DetailProductActivity
import fr.unilasalle.androidtp.beans.Product
import fr.unilasalle.androidtp.databinding.ProductItemBinding

/**
 * Adapter pour les produits
 * @property productList
 *    Liste des produits
 *    @see Product
 *    @see RecyclerView.Adapter
 *    @see ProductViewHolder
 *    @see ProductItemBinding
 *    @see onCreateViewHolder
 *    @see onBindViewHolder
 *    @see getItemCount
 */
class ProductAdapter(private val productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private lateinit var binding: ProductItemBinding // Binding pour les produits

    /**
     * ViewHolder pour les produits (éléments de la liste)
     * @property binding
     *   Binding pour les produits
     *   @see ProductItemBinding
     *   @see RecyclerView.ViewHolder
     *   @see ProductViewHolder
     */
    class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Création de la vue
     * @param parent
     * @param viewType
     * @return ProductViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    /**
     * Affectation des valeurs aux vues
     * @param holder
     * @param position
     */
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

        holder.itemView.setOnClickListener {

            // Création de l'Intent
            val intent = Intent(holder.itemView.context, DetailProductActivity::class.java)

            // Ajout du produit dans l'Intent
            intent.putExtra("product", productList[position])

            // Démarrage de l'activité
            holder.itemView.context.startActivity(intent)
        }
    }

    /**
     * Retourne le nombre d'éléments de la liste
     */
    override fun getItemCount() = productList.size
}
