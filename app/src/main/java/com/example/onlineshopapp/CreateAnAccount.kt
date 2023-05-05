package com.example.onlineshopapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.Toast

class CreateAnAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_an_account)

        supportActionBar?.hide()

        // add a clickListener buttonCreate
        val createAccountButton = findViewById<Button>(R.id.buttonCreate)
        createAccountButton.setOnClickListener {
            // show a success message
            val toast = Toast.makeText(this, "Register successfully created", Toast.LENGTH_SHORT)
            toast.show()

            // open MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
