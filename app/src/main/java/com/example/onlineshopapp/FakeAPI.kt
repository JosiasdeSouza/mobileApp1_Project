package com.example.onlineshopapp

import android.content.ClipData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeAPI {
    @GET("categories")
    suspend fun getCategories(): List<Categories>

    @GET("categories/{categoryId}/items")
    suspend fun getItems(@Path("categoryId") categoryId: Int): List<ClipData.Item>
}
val retrofit = Retrofit.Builder()
    .baseUrl("https://fakestoreapi.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val fakeApi = retrofit.create(FakeAPI::class.java)