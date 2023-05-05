package com.example.onlineshopapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.onlineshopapp.databinding.ActivityMainBinding
import com.example.onlineshopapp.models.Auth
import com.example.onlineshopapp.models.Token
import com.google.gson.Gson
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val client = OkHttpClient()
    private val gson = Gson()
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        sharedPrefs = getSharedPreferences("onlineshopapp", Context.MODE_PRIVATE)

        // Recover the saved credentials from SharedPreferences
        val savedUsername = sharedPrefs.getString("username", null)
        val savedPassword = sharedPrefs.getString("password", null)
        if (!savedUsername.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
            binding.usernameInput.setText(savedUsername)
            binding.editPassword.setText(savedPassword)
        }

        binding.buttonSignIn.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val password = binding.editPassword.text.toString()
            signIn(username, password)
        }

        binding.buttonCreate.setOnClickListener {
            val intent = Intent(this, CreateAnAccount::class.java)
            startActivity(intent)
            finish()
        }
        val aboutButton = findViewById<Button>(R.id.about_button)
        aboutButton.setOnClickListener {
            showAboutDialog()
        }
    }

    private fun showAboutDialog() {
        val aboutText = "Online Shop App\n\nCopyright (c) Josias de Souza"
        val builder = AlertDialog.Builder(this)
        builder.setTitle("About this app")
        builder.setMessage(aboutText)
        builder.setPositiveButton("OK", null)
        builder.create().show()}

    @SuppressLint("CommitPrefEdits")
    private fun signIn(username: String, password: String) {
        val auth = Auth(username, password)
        val request = getTokenAttachedRequestBuilder()
            .url("https://fakestoreapi.com/auth/login")
            .post(gson.toJson(auth).toRequestBody())
            .header("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Log.i("MAIN_ACT", "Failure: $e")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    if (response.isSuccessful) {
                        val responseString = response.body?.string()
                        val tokenObject = gson.fromJson(responseString, Token::class.java)

                        // Save the credentials in SharedPreferences
                        sharedPrefs.edit().apply {
                            putString("username", username)
                            putString("password", password)
                            apply()
                        }

                        runOnUiThread {
                            Log.i("MAIN_ACT", "Success: $responseString")
                            Log.i("MAIN_ACT", "Token: ${tokenObject.token}")
                            val intent = Intent(this@MainActivity, Categories::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                this@MainActivity,
                                "Unsuccessful ${response.code}",
                                Toast.LENGTH_LONG
                            ).show()
                            Log.i("MAIN_ACT", "Unsuccessful ${response.code}")
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Log.e("MAIN_ACT", "Error: $e")
                    }
                }
            }
        })
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getTokenAttachedRequestBuilder(): Request.Builder {
        val token = sharedPrefs.getString("token", "")
        val requestBuilder = Request.Builder()
        return if (token!!.isNotEmpty())
            requestBuilder.header("Authorization", "Bearer $token")
        else requestBuilder
    }
}
