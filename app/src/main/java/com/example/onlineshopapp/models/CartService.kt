package com.example.onlineshopapp.models

import Cart
import retrofit2.Call
import retrofit2.http.*

interface CartService {
    @POST("carts")
    suspend fun createCart(): Cart

    @POST("carts/{cart_id}/items")
    suspend fun addItemToCart(
        @Path("cart_id") cartId: Long,
        @Body item: CartItem
    ): Cart

    @GET("carts/{cart_id}")
    suspend fun getCart(
        @Path("cart_id") cartId: Long
    ): Cart

    @DELETE("carts/{cart_id}")
    suspend fun clearCart(
        @Path("cart_id") cartId: Long
    ): Cart

    abstract fun getUser(i: Int): User
}

