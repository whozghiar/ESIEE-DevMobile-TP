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
import fr.unilasalle.androidtp.model.CartItemWithProduct
import fr.unilasalle.androidtp.model.Product


class CartItemAdapter (
) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {



    var cartItems = listOf<CartItemWithProduct>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    // Variable pour le listener
    var listener: CartItemListener? = null

    // Interface pour les callbacks du listener
    interface CartItemListener {
        fun onDecreaseQuantity(cartItem: CartItem)
        fun onRemove(cartItem: CartItem)
    }


    override fun getItemCount(): Int = cartItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = cartItems[position]
        val product = cartItem.product
        holder.bind(cartItem)
    }

    inner class CartItemViewHolder(catItemView : View) : RecyclerView.ViewHolder(catItemView) {

        // Binding avec le layout cart_item.xml
        private val binding : CartItemBinding = CartItemBinding.bind(catItemView)
        fun bind(item: CartItemWithProduct) {
            val prix = item.product.price * item.cartItem.quantity
            binding.idProductTitle.text = item.product.title
            binding.idPriceProduct.text = "${String.format("%.2f", prix)} €"
            binding.idQuantityProduct.text = item.cartItem.quantity.toString()


            Glide.with(binding.root)
                .load(item.product.image)
                .into(binding.idMiniatureProduct)


            binding.idSupprimer.setOnClickListener {
                val itemToDecrease = cartItems[adapterPosition]
                if(itemToDecrease.cartItem.quantity > 1) {
                    Log.d("CartItemAdapter", "Decrease quantity because quantity > 1")
                    listener?.onDecreaseQuantity(itemToDecrease.cartItem)
                } else {
                    Log.d("CartItemAdapter", "Remove item because quantity = 1")
                    listener?.onRemove(itemToDecrease.cartItem)
                }
            }


        }
    }
}


