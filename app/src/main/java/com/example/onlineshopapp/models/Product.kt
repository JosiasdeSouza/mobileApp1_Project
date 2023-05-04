package com.example.onlineshopapp.models

import java.io.Serializable


class Product(
    val id: Int,
    val title: String,
    val price: Float,
    val category: String,
    val description: String,
    val image: String?,
    val rating: Rating
) : Serializable  {

    val image_url: String
        get() = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"

    class Rating (
        val rate: Double,
        val count: Int
    ): Serializable
}







