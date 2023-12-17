package fr.unilasalle.androidtp.adapters
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.unilasalle.androidtp.R
import fr.unilasalle.androidtp.databinding.CartItemBinding
import fr.unilasalle.androidtp.model.CartItem
import fr.unilasalle.androidtp.model.Product


class CartItemAdapter (
) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    var cartItems: List<CartItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var products : Map<Int, Product> = mapOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // Variable pour le listener
    var listener: CartItemListener? = null

    // Interface pour les callbacks du listener
    interface CartItemListener {
        fun onDecreaseQuantity(cartItem: CartItem)
        fun onRemoveItem(cartItem: CartItem)
    }


    override fun getItemCount(): Int = cartItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = cartItems[position]
        val product = products[cartItem.productId]
        holder.bind(cartItem, product)
    }

    inner class CartItemViewHolder(catItemView : View) : RecyclerView.ViewHolder(catItemView) {

        // Binding avec le layout cart_item.xml
        private val binding : CartItemBinding = CartItemBinding.bind(catItemView)
        fun bind(item: CartItem, product: Product?) {

            val prix = product?.price?.times(item.quantity)
            Log.d("CartItemAdapter", "Prix : $prix")
            binding.idProductTitle.text = "Produit : ${product?.title}"
            binding.idPriceProduct.text = "Prix : ${String.format("%.2f", prix)} €"
            binding.idQuantityProduct.text = "Quantité : ${item.quantity}"

            Glide.with(binding.root)
                .load(product?.image)
                .into(binding.idMiniatureProduct)

            binding.idSupprimer.setOnClickListener {
                if(listener != null) {
                    if(item.quantity > 1) {
                        item.quantity--
                        listener!!.onDecreaseQuantity(item)
                    } else {
                        listener!!.onRemoveItem(item)
                    }
                }
            }
        }
    }
}


