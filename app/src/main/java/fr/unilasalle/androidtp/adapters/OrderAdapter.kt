package fr.unilasalle.androidtp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.unilasalle.androidtp.R
import fr.unilasalle.androidtp.databinding.OrderBinding
import fr.unilasalle.androidtp.model.OrderWithCart

class OrderAdapter () : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){

    var orders = listOf<OrderWithCart>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order, parent, false)
        return OrderViewHolder(view)
    }
    override fun getItemCount(): Int = orders.size
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    inner class OrderViewHolder(orderView : View) : RecyclerView.ViewHolder(orderView){

        private val binding : OrderBinding = OrderBinding.bind(orderView)
        fun bind(item: OrderWithCart) {
            binding.textViewOrderDate.text = item.order.date
            binding.textViewOrderPrice.text = "${String.format("%.2f", item.cart.price)} €"
            binding.textViewOrderId.text = item.order.id.toString()
        }
    }
}