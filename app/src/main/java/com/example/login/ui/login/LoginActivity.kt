package com.example.login.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import com.apollographql.apollo.exception.ApolloException
import com.example.login.MainActivity

import com.example.login.R
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.File
import kotlin.random.Random

class LoginActivity : AppCompatActivity() {
    lateinit var apolloClient: ApolloClient
    private lateinit var loginViewModel: LoginViewModel
    val url= "https://jsonplaceholder.typicode.com/posts"
    val jsonobj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //////////////////////////////
        var file = File(this.applicationContext.filesDir,"Rick")
        val size : Long = 1024*1024
        val cacheStore = DiskLruHttpCacheStore(file, size)
        val base_url = "https://rickandmortyapi.com/graphql"
        //var resultado : List<FeedResultQuery.Result>? = null

        apolloClient = ApolloClient.builder()
            .serverUrl(base_url)
            .httpCache(ApolloHttpCache(cacheStore))
            .okHttpClient(OkHttpClient()).build()

        /*
        var botao = findViewById<Button>(R.id.botao)
        val label = findViewById<TextView>(R.id.resultado)
        botao.setOnClickListener(){
            apolloClient.query(FeedResultQuery.builder().build())
                .httpCachePolicy(HttpCachePolicy.CACHE_FIRST)
                .enqueue(object : ApolloCall
                .Callback<FeedResultQuery.Data>(){
                    override fun onFailure(e: ApolloException) {
                        TODO("Not yet implemented")
                    }

                    override fun onResponse(response: Response<FeedResultQuery.Data>) {
                        Log.d("TAG", " " + response.data + "\n\n\n\n")

                        resultado = response.data?.characters()?.results()
                    }

                })
            botao.setBackgroundColor(Random.nextInt(100))
            label.setText(resultado?.get(Random.nextInt(resultado!!.size))?.name()).toString()

        }
        */////////////////////////////////////
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<TextView>(R.id.textview_register)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val intent = Intent(this, MainActivity::class.java)
        val intent2 = Intent(this, RegisterActivity::class.java)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
                .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })


        /*Ação botão entre sem login*/
        val breaklogin : Button = findViewById(R.id.breaklogin)
        breaklogin.setOnClickListener {
            val intentbreak = Intent(this, MainActivity::class.java)
            startActivity(intentbreak)
        }


        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                                username.text.toString(),
                                password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {

                //jsonobj.put("username", username.text.toString())
                //jsonobj.put("password", password.text.toString())
                //val queue = Volley.newRequestQueue(this@LoginActivity)
                //val request = JsonObjectRequest(Request.Method.POST, url, jsonobj,
                //    Response.Listener {
                        // start your next activity
                //          response -> startActivity(intent)
                //  }, Response.ErrorListener {
                //  Toast.makeText(this@LoginActivity, "something is wrong, try again" , Toast.LENGTH_SHORT).show()
                //  })
                //queue.add(request)
                startActivity(intent)
            }

            //register.setOnClickListener {
                // start your next activity
              //  startActivity(intent2)
            //}

        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
                applicationContext,
                "$welcome $displayName",
                Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }




}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
