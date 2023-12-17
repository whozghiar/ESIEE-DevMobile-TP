package fr.unilasalle.androidtp.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.unilasalle.androidtp.R
import fr.unilasalle.androidtp.databinding.CartItemBinding
import fr.unilasalle.androidtp.databinding.ProductItemBinding
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.Product
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository


class CartItemAdapter (
    //private val onSuppressionListener : onSuppressionClickListener
) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    var list: List<CartItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var products: List<Product> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = list[position]
        holder.bind(cartItem)
    }

    inner class CartItemViewHolder(catItemView : View) : RecyclerView.ViewHolder(catItemView) {

        // Binding avec le layout cart_item.xml
        private val binding : CartItemBinding = CartItemBinding.bind(catItemView)
        fun bind(item: CartItem) {

            // TODO : Affectation des valeurs aux vues pour chaque CartItem
            // TODO : Bouton suppression d'un produit du panier
            // TODO : Chargement de l'image
        }
    }
}


