package com.example.onlineshopapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.onlineshopapp.models.Product
import com.squareup.picasso.Picasso


class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var product: Product
    private var quantity: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        supportActionBar?.hide()

        // Get the selected product from the intent
        product = intent.getSerializableExtra("product") as Product

        // Update the UI with the product details
        findViewById<TextView>(R.id.product_title).text = product.title
        findViewById<TextView>(R.id.product_price).text = "Price: €${product.price}"
        findViewById<TextView>(R.id.product_description).text = product.description

        val imageView = findViewById<ImageView>(R.id.product_image)
        if (product.image != null) {
            Picasso.get().load(product.image).into(imageView)
        } else {
            Picasso.get().load(product.image_url).into(imageView)
        }

        findViewById<TextView>(R.id.count_view).text = quantity.toString()

        findViewById<Button>(R.id.decrease_quantity_button).setOnClickListener {
            if (quantity > 1) {
                quantity--
                findViewById<TextView>(R.id.count_view).text = quantity.toString()
            }
        }

        findViewById<Button>(R.id.increase_quantity_button).setOnClickListener {
            quantity++
            findViewById<TextView>(R.id.count_view).text = quantity.toString()
        }

        findViewById<Button>(R.id.buy_button).setOnClickListener {
            try {
                // Add the product to the cart
                Cart.addProduct(product, quantity)

                // Start the CartActivity
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("ProductDetailsActivity", "Error adding product to cart", e)
            }
        }
    }

}


