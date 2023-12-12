package fr.unilasalle.androidtp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.unilasalle.androidtp.beans.Product
import fr.unilasalle.androidtp.beans.ShoppingCart
import fr.unilasalle.androidtp.databinding.CartItemBinding

class CartAdapter(private val cartItems: List<Product>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private lateinit var binding: CartItemBinding // Binding pour les cartItems (produits dans le panier)

    /**
     * ViewHolder pour les produits (éléments de la liste)
     */
    class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

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
        val cartItem = cartItems[position]
        with(holder.binding) {

            binding.idProductTitle.text = cartItem.title
            binding.idQuantityProduct.text = "${ShoppingCart.getQuantity(cartItem)}"
            //binding.text = "${ShoppingCart.getQuantity(cartItem) * cartItem.price}"

            Glide.with(binding.root)
                .load(cartItem.image)
                .into(binding.idMiniatureProduct).toString()
        }
    }

    /**
     * Retourne le nombre d'éléments de la liste
     */
    override fun getItemCount() = cartItems.size
}