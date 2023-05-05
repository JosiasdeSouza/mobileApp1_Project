package com.example.onlineshopapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshopapp.R
import com.example.onlineshopapp.models.CartItem
import kotlin.collections.sumByDouble

class CartItemAdapter(private val cartList: List<CartItem>) :
    RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartIdView: TextView = itemView.findViewById(R.id.cart_id)
        val cartUserView: TextView = itemView.findViewById(R.id.cart_user_id)
        val cartDateView: TextView = itemView.findViewById(R.id.cart_date)
        val cartProductIdView: TextView = itemView.findViewById(R.id.cart_products_id)
        val cartQuantityView: TextView = itemView.findViewById(R.id.cart_quantity)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartList[position]

        holder.cartIdView.text = "ID ${cartItem.id}"
        holder.cartUserView.text = "User: ${cartItem.userId}"
        holder.cartDateView.text = "Date: ${cartItem.date}"
        holder.cartProductIdView.text = "Product: ${cartItem.product_id}"
        holder.cartQuantityView.text = "Total Quantity: ${cartItem.quantity}"

    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}

private operator fun Double.plus(any: Any) {

}

private fun <E> List<E>.fold(initial: Double, operation: (acc: Double, E) -> Unit) {

}

private fun Any.toDouble(): Any {
    return this

}

private operator fun Any.times(quantity: Int): Any {
    return this

}
