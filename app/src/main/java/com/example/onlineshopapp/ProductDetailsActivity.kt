package com.example.onlineshopapp

import Cart
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.onlineshopapp.models.CartItem
import com.example.onlineshopapp.models.CartService
import com.example.onlineshopapp.models.Product
import com.example.onlineshopapp.models.User
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var product: Product
    private var quantity: Int = 1
    private lateinit var cartService: CartService
    private lateinit var cart: Cart
    private lateinit var user: User
    private var numItemsInCart: Int = 0

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        supportActionBar?.hide()

        // Get the selected product from the intent
        product = intent.getSerializableExtra("product") as Product

        // Update the UI with the product details
        findViewById<TextView>(R.id.product_id).text = "ID: ${product.id}"

        findViewById<TextView>(R.id.product_title).text = "Title: ${product.title}"

        findViewById<TextView>(R.id.product_price).text = "Price: €${product.price}"
        findViewById<TextView>(R.id.product_price).setGravity(Gravity.END or Gravity.TOP)

        findViewById<TextView>(R.id.product_description).text = "Description: ${product.description}"

        val imageView = findViewById<ImageView>(R.id.product_image)
        if (product.image != null) {
            Picasso.get().load(product.image).into(imageView)
        } else {
            Picasso.get().load(product.image_url).into(imageView)
        }

        // Initialize the cart and user objects
        cart = Cart(User(1, "Guest", ""), mutableListOf())

        // Initialize the number of items in the cart
        numItemsInCart = cart.items.size

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
            cartService = ApiClient.getCartService()

            // Get the user object from the API
            GlobalScope.launch {
                user = cartService.getUser(1)
                cart = Cart(user, mutableListOf())

                // Add the selected product to the cart
                val cartItem = CartItem(product, quantity)
                cart.items.add(cartItem)

                // Update the number of items in the cart
                numItemsInCart = cart.items.size

                // Update the cart icon in the UI
                updateCartIcon()

                // Clear the quantity counter
                quantity = 1
                findViewById<TextView>(R.id.count_view).text = quantity.toString()
            }
        }


        // Set the initial number of items in the cart icon
        updateCartIcon()
    }

    private fun CartItem(id: Int, user: Int): CartItem {
        return CartItem(id, user)

    }

    private fun updateCartIcon() {
        val cartIcon = findViewById<ImageView>(R.id.shopping_cart)
        numItemsInCart = cart.items.size
        if (numItemsInCart > 0) {
            cartIcon.setImageResource(R.drawable.add_shopping_cart)
        } else {
            cartIcon.setImageResource(R.drawable.shopping_cart_empty)
        }
    }
}

