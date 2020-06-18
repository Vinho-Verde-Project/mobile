package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity()  {

    private val url= "https://jsonplaceholder.typicode.com/posts"
    private val jsonObj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.login)
        val intent = Intent(this, MainActivity::class.java)

        btnLogin.setOnClickListener{
            jsonObj.put("email", email.text)
            jsonObj.put("password", password.text)
            val queue = Volley.newRequestQueue(this@LoginActivity)
            val request = JsonObjectRequest(
                Request.Method.POST, url, jsonObj,
                Response.Listener {
                        response -> startActivity(intent)
                    Toast.makeText(this@LoginActivity, "Valid Login" , Toast.LENGTH_SHORT).show()

                }, Response.ErrorListener { error ->
                    Toast.makeText(this@LoginActivity, "Invalid Login" , Toast.LENGTH_SHORT).show()
                })
            queue.add(request)

        }

    }

}