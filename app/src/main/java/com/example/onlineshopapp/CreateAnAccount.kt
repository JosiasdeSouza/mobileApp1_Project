package com.example.onlineshopapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class CreateAnAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_an_account)

        supportActionBar?.hide()
    }
}