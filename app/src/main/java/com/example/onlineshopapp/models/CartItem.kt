package com.example.onlineshopapp.models

class CartItem(
    val id: Product,
    val userId: Int,
    val date: Long,
    val product_id: String,
    var quantity: Int,


    ) {
    val product: Any
        get() = 1
    val price: Any
        get() = 1
}
