package com.example.onlineshopapp.models

import Cart
import retrofit2.Call
import retrofit2.http.*

interface CartService {
    @POST("carts")
    suspend fun createCart(): Cart

    @PUT("carts/{id}")
    suspend fun updateCart(@Path("id") id: Int, @Body cart: Cart): Cart

    @DELETE("carts/{id}")
    suspend fun deleteCart(@Path("id") id: Int)
    @POST("carts")
    fun addToCart(@Query("productId") productId: Int): Call<CartItem>
    abstract fun onSuccess(cartItems: List<CartItem>)
}


