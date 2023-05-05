package com.example.onlineshopapp

import com.example.onlineshopapp.models.CartService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://fakestoreapi.com/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val instance: FakeAPI by lazy {
        retrofit.create(FakeAPI::class.java)
    }

    val cartInstance: FakeAPI by lazy {
        retrofit.create(FakeAPI::class.java)
    }

}
