package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

class LoginActivity : AppCompatActivity()  {

    private val jsonObj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.login)
        val intent = Intent(this, MainActivity::class.java)
        val testes = findViewById<TextView>(R.id.textview_login)
        val globalClass = GlobalClass()
        val url = globalClass.networkIP

        fun validCert() {
            try {
                val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        val acceptedIssuers: Array<Any?>
                            get() = arrayOfNulls(0)

                        override fun checkClientTrusted(
                            certs: Array<X509Certificate?>?,
                            authType: String?
                        ) {
                        }

                        override fun checkServerTrusted(
                            certs: Array<X509Certificate?>?,
                            authType: String?
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            TODO("Not yet implemented")
                        }
                    }
                )
                val sc: SSLContext = SSLContext.getInstance("SSL")
                sc.init(null, trustAllCerts, SecureRandom())
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory())
                HttpsURLConnection.setDefaultHostnameVerifier(object : HostnameVerifier {
                    override fun verify(arg0: String?, arg1: SSLSession?): Boolean {
                        return true
                    }
                })
            } catch (e: Exception) {
            }
        }

        validCert()

        btnLogin.setOnClickListener{
            val inputMail: String = "\"" + email.text + "\""
            val inputPass: String = "\"" + password.text + "\""
            val inputQuery: String = "query { employeeEmail ( email: " + inputMail + ", password: " + inputPass + "){ username }}"
            jsonObj.put("query", inputQuery)

            val queue = Volley.newRequestQueue(this@LoginActivity)
            val request = JsonObjectRequest(
                Request.Method.POST, url, jsonObj,
                Response.Listener {
                    response ->
                    if(response.getString("employeeEmail") != "null"){
                        Toast.makeText(this@LoginActivity, "Bem vindo, " + response.getJSONObject("employeeEmail").getString("username").capitalize() , Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@LoginActivity, "Email ou Senha Inválidos", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener { error ->

                    Toast.makeText(this@LoginActivity, "Ocorreu o erro: " + error , Toast.LENGTH_SHORT).show()
                })
            queue.add(request)

        }
        /*Ação botão entre sem login*/
        /*val breaklogin : Button = findViewById(R.id.breaklogin)
        breaklogin.setOnClickListener {
            val intentbreak = Intent(this, MainActivity::class.java)
            startActivity(intentbreak)
        }*/

    }

}