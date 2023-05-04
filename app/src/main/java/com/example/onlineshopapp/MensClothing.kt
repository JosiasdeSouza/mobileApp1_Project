package com.example.onlineshopapp


import android.content.Intent
import com.example.onlineshopapp.adapter.RecyclerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshopapp.models.Product
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MensClothing : AppCompatActivity() {
    private var products: List<Product> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mens_clothing)

        supportActionBar?.hide()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val url = "https://fakestoreapi.com/products/category/men's%20clothing"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                    products = Gson().fromJson(responseBody, Array<Product>::class.java).toList()
                    runOnUiThread {
                        val adapter = RecyclerAdapter(products) { position ->

                            val intent = Intent(this@MensClothing, ProductDetailsActivity::class.java)
                            intent.putExtra("product", products[position])
                            startActivity(intent)
                        }

                        recyclerView.adapter = adapter
                        recyclerView.setHasFixedSize(true)
                        recyclerView.layoutManager = LinearLayoutManager(this@MensClothing)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }
}