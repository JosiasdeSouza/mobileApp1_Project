package com.example.onlineshopapp

import com.example.onlineshopapp.models.CartItem
import com.example.onlineshopapp.Categories
import com.example.onlineshopapp.models.Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeAPI {
    @GET("categories")
    suspend fun getCategories(): List<Categories>

    @GET("categories/{categoryId}/items")
    suspend fun getItems(@Path("categoryId") categoryId: Int): List<Product>

    @GET("carts/1/items")
    fun getCartItems(): Call<List<CartItemResponse>>

    // Note: this function is not defined in the API, you may need to implement it yourself
    // or use a different API endpoint if available.
    fun addToCart(productId: Any): Any
}

data class CartItemResponse(
    val id: Int,
    val quantity: Int
)

val retrofit = Retrofit.Builder()
    .baseUrl("https://fakestoreapi.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val fakeApi = retrofit.create(FakeAPI::class.java)
