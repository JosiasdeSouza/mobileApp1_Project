package com.example.onlineshopapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

class CartActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private var cartArray = JSONArray()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Get the data from the intent
        val bundle = intent.extras
        val cartId = bundle?.getString("Id")
        val cartProduct = bundle?.getString("productId")
        val cartQuantity = bundle?.getString("productQuantity")

        // Display the data in the UI
        val cartProductIdView = findViewById<TextView>(R.id.cart_products_id)
        cartProductIdView.text = cartProduct

        val cartIdView = findViewById<TextView>(R.id.cart_id)
        cartIdView.let{
            it.text = cartId
        }


        val cartQuantityView = findViewById<TextView>(R.id.cart_quantity)
        cartQuantityView.text = cartQuantity
    }

    override fun onStart() {
        super.onStart()

        // add products to cart
        val addProductButton = findViewById<TextView>(R.id.checkout_button)
        addProductButton.setOnClickListener {
            val cartIdView = findViewById<TextView>(R.id.cart_id)
            val cartId = cartIdView.text.toString()
            val cartUserView = findViewById<TextView>(R.id.cart_user_id)
            val userId = cartUserView.text.toString()
            val cartDateView = findViewById<TextView>(R.id.cart_date)
            val date = cartDateView.text.toString()
            val cartProductIdView = findViewById<TextView>(R.id.cart_products_id)
            val productId = cartProductIdView.text.toString()
            val cartQuantityView = findViewById<TextView>(R.id.cart_quantity)
            val quantity = cartQuantityView.text.toString()

            addProductToCart(cartId, userId, date, productId, quantity)
        }
    }

    // function to get cart data from API
    private suspend fun getCartData(): Response? {
        val request = Request.Builder()
            .url("https://fakestoreapi.com/carts")
            .build()

        // suspend until the API response is received
        return withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }
    }

    // function to add product to cart
    private fun addProductToCart(
        cartId: String,
        userId: String,
        date: String,
        productId: String,
        quantity: String,
    ) {
        val cart = cartArray.getJSONObject(0)
        val productsArray = cart.getJSONArray("products")

        val product = JSONObject()
        product.put("productId", productId)
        product.put("quantity", quantity)
        product.put("cartId", cartId)
        product.put("date", date)
        product.put("userId", userId)

        productsArray.put(product)

        // update UI
        updateUI()
    }

    // function to update UI with cart data
    private fun updateUI() {
        val cartIdView = findViewById<TextView>(R.id.cart_id)
        val cartUserView = findViewById<TextView>(R.id.cart_user_id)
        val cartDateView = findViewById<TextView>(R.id.cart_date)
        val cartProductIdView = findViewById<TextView>(R.id.cart_products_id)
        val cartQuantityView = findViewById<TextView>(R.id.cart_quantity)

        if (cartIdView != null && cartUserView != null && cartDateView != null &&
            cartProductIdView != null && cartQuantityView != null
        ) {
            // update the text of the TextViews with the cart data
            val cart = cartArray.getJSONObject(0)
            cartIdView.text = cart.getString("id")
            cartUserView.text = cart.getString("userId")
            cartDateView.text = cart.getString("date")

            // build a string with the product ids and quantities
            val productsArray = cart.getJSONArray("products")
            val productsStringBuilder = StringBuilder()
            for (i in 0 until productsArray.length()) {
                val product = productsArray.getJSONObject(i)
                val productId = product.getString("productId")
                val quantity = product.getInt("quantity")
                productsStringBuilder.append("$productId x $quantity\n")
            }
            val checkOutButton = findViewById<TextView>(R.id.checkout_button)
            checkOutButton.setOnClickListener {
                val intent = Intent(this, CartActivity::class.java)
                intent.putExtra("Id", "1")
                intent.putExtra("userId", "1")
                intent.putExtra("date", "2020-03-02T00:00:00.000Z")
                intent.putExtra("productId", "1")
                intent.putExtra("productQuantity", "4")
                intent.putExtra("productId", "2")
                intent.putExtra("productQuantity", "1")
                intent.putExtra("productId", "3")
                intent.putExtra("productQuantity", "3")
                startActivity(intent)
                cartProductIdView.text = productsStringBuilder.toString()

                // calculate the total quantity of products
                var totalQuantity = 0
                for (i in 0 until productsArray.length()) {
                    val product = productsArray.getJSONObject(i)
                    totalQuantity += product.getInt("quantity")
                }
                cartQuantityView.text = totalQuantity.toString()
            }
        }
    }
}
