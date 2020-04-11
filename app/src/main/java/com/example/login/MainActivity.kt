package com.example.login

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import com.example.login.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

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

        getSupportActionBar()?.setDisplayShowHomeEnabled(true)// set drawable icon
        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_action_bar);// set drawable icon
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        //supportActionBar?.setIcon(R.drawable.ic_action_bar)

        //getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME ,ActionBar.DISPLAY_USE_LOGO);
        //actionBar?.setIcon(R.drawable.ic_action_bar)

        //Part1
        val entries = ArrayList<Entry>()

//Part2
        entries.add(Entry(1f, 10f))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 7f))
        entries.add(Entry(4f, 20f))
        entries.add(Entry(5f, 16f))

//Part3
        val vl = LineDataSet(entries, "My Type")

//Part4
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.colorPrimary
        vl.fillAlpha = R.color.colorAccent

//Part5
        linechart.xAxis.labelRotationAngle = 0f

//Part6
        linechart.data = LineData(vl)

//Part7
        linechart.axisRight.isEnabled = false
        linechart.xAxis.axisMaximum = +0.1f

//Part8
        linechart.setTouchEnabled(true)
        linechart.setPinchZoom(true)

//Part9
        linechart.description.text = "Days"
        linechart.setNoDataText("No forex yet!")

//Part10
        linechart.animateX(1800, Easing.EaseInExpo)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main_menu,menu)
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




}
