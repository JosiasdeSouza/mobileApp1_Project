package com.example.onlineshopapp

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import okhttp3.*
import org.json.JSONArray
import java.io.IOException


class Categories : AppCompatActivity() {

    private lateinit var categoriesListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        supportActionBar?.hide()

        categoriesListView = findViewById(R.id.categoriesListView)

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://fakestoreapi.com/products/categories")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                runOnUiThread {
                    try {
                        val json = JSONArray(responseBody)
                        val categories = mutableListOf<String>()
                        for (i in 0 until json.length()) {
                            categories.add(json.getString(i))
                        }
                        val adapter = ArrayAdapter(
                            this@Categories,
                            android.R.layout.simple_list_item_1,
                            categories
                        )
                        categoriesListView.setOnItemClickListener { _, _, position, _ ->
                            val category = categories[position]
                            val intent = when (category) {
                                "electronics" -> Intent(this@Categories, Electronics::class.java)
                                "jewelery" -> Intent(this@Categories, Jewelery::class.java)
                                "men's clothing" -> Intent(this@Categories, MensClothing::class.java)
                                "women's clothing" -> Intent(this@Categories, WomensClothing::class.java)
                                else -> throw IllegalArgumentException("Categoria desconhecida: $category")
                            }
                            intent.putExtra("category", category)
                            startActivity(intent)
                        }

                        categoriesListView.adapter = adapter

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}
