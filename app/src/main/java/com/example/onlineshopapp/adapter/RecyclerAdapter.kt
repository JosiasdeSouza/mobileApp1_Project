package com.example.onlineshopapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshopapp.R
import com.example.onlineshopapp.models.Product
import com.squareup.picasso.Picasso

class RecyclerAdapter(private val productList: List<Product>, private val onItemClickListener: (Int) -> Unit) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val products = productList[position]
        holder.idTextView.text = "ID: ${products.id}"
        holder.titleTextView.text = products.title
        holder.categoryTextView.text = products.category
        holder.priceTextView.text = "$${products.price}"
        holder.descriptionTextView.text = products.description
        if (products.image != null) {
            Picasso.get().load(products.image).into(holder.imageView)
        } else {
            Picasso.get().load(products.image_url).into(holder.imageView)
        }
        holder.ratingTextView.text = "Rating: ${products.rating.rate}"
        holder.ratingCountTextView.text = "Rating count: ${products.rating.count}"
    }

    override fun getItemCount(): Int {
        return productList.size
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.id_prod)
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val categoryTextView: TextView = itemView.findViewById(R.id.category)
        val priceTextView: TextView = itemView.findViewById(R.id.price)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val ratingTextView: TextView = itemView.findViewById(R.id.rate)
        val ratingCountTextView: TextView = itemView.findViewById(R.id.count)

        init {
            itemView.setOnClickListener {
                onItemClickListener(adapterPosition)
            }
        }

    }

}

