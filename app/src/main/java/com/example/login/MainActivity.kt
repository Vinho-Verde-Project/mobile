package com.example.login

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.login.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IVolley {

    private var mRequestQueue:RequestQueue?=null
    val url= "https://jsonplaceholder.typicode.com/posts"
    private var iVolley:IVolley?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(

                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        supportActionBar?.setDisplayShowHomeEnabled(true)// set drawable icon
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_bar);// set drawable icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        val queue = Volley.newRequestQueue(this@MainActivity)
        val request = StringRequest(Request.Method.GET,url, Response.Listener {
                response -> Toast.makeText(this@MainActivity, ""+response, Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener {
            Toast.makeText(this@MainActivity, "something is wrong", Toast.LENGTH_SHORT).show()
        })
        queue.add(request)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, LoginActivity::class.java)
        val intent2 = Intent(this, AboutActivity::class.java)
        when(item.itemId){
            R.id.sair -> startActivity(intent)
            R.id.about -> startActivity(intent2)
        }
        return true
    }

    override fun onResponse(response: String) {
        //show toast
        Toast.makeText(this@MainActivity, ""+response, Toast.LENGTH_SHORT).show()
    }


}
