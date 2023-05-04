package com.example.onlineshopapp

import android.content.Intent
import com.example.onlineshopapp.adapter.RecyclerAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshopapp.models.Product
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Jewelery : AppCompatActivity() {

    private var products: List<Product> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jewelery)

        supportActionBar?.hide()

            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)

            val url = "https://fakestoreapi.com/products/category/jewelery"
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        products = Gson().fromJson(responseBody, Array<Product>::class.java).toList()
                        runOnUiThread {
                            val adapter = RecyclerAdapter(products) { position ->

                                val intent = Intent(this@Jewelery, ProductDetailsActivity::class.java)
                                intent.putExtra("product", products[position])
                                startActivity(intent)
                            }

                            recyclerView.adapter = adapter
                            recyclerView.setHasFixedSize(true)
                            recyclerView.layoutManager = LinearLayoutManager(this@Jewelery)
                        }
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
            })
        }
    }