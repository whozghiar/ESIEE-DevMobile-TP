package fr.unilasalle.androidtp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.unilasalle.androidtp.Activities.OnItemClickListener
import fr.unilasalle.androidtp.model.Product
import fr.unilasalle.androidtp.databinding.CartItemBinding


class CartAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private lateinit var binding: CartItemBinding // Binding pour les cartItems (produits dans le panier)

    var cartItems : List<Product> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     * ViewHolder pour les produits (éléments de la liste)
     */
    class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root){

        /**
         * Affectation des valeurs aux vues
         * @param product
         * @see Product
         * @see CartItemBinding
         */
        fun bind(product: Product, listener: OnItemClickListener){

            /*
            val productTitle = product.title
            val productQuantity = ShoppingCart.getQuantity(product)
            val productPrice = product.price

            // Affectation des valeurs aux vues pour chaque produit
            binding.idProductTitle.text = "Produits : ${productTitle}"
            binding.idQuantityProduct.text = "Quantité : ${productQuantity}"
            binding.idPriceProduct.text= "Prix : ${productPrice * productQuantity}€"

            // Chargement de l'image
            Glide.with(binding.root)
                .load(product.image)
                .into(binding.idMiniatureProduct)


            // Bouton suppression d'un produit du panier
            binding.idSupprimer.setOnClickListener {
                Log.d("CartAdapter", "Suppression du produit ${product.title}")
                listener.onDeleteProductDelete(product)
            }

             */


        }

    }

    /**
     * Création de la vue
     * @param parent
     * @param viewType
     * @return CartViewHolder
     * @see CartViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    /**
     * Affectation des valeurs aux vues
     * @param holder
     * @param position
     * @see CartViewHolder
     * @see CartItemBinding
     */
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = cartItems[position]
        holder.bind(cart,listener)
    }

    /**
     * Retourne le nombre d'éléments de la liste
     */
    override fun getItemCount() = cartItems.size
}
