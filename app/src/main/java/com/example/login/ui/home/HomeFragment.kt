package com.example.login.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.login.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    val url= "https://127.0.0.1:53832/graphql"
    val jsonobj = JSONObject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        var  textR = root.findViewById<TextView>(R.id.text_home)
        jsonobj.put("query"," { employees { id username firstName lastName nif birthdate adress phone email hashedPassword createdAt roleId} }")
        val queue = Volley.newRequestQueue(this.context)
        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonobj,
            Response.Listener {
                //start your next activity
                    responce -> textR.setText("-- " + responce.toString() + " --")
                //response -> startActivity(intent)
            }, Response.ErrorListener {
                textR.setText("-- " + jsonobj.toString() + " --")
                Toast.makeText(this.context, "something is wrong, try again" , Toast.LENGTH_SHORT).show()
            })
        queue.add(request)




        val pieChart : PieChart = root.findViewById(R.id.pieChart)

        var entryPie : ArrayList<PieEntry> = ArrayList() // Colocando valores para X e Y
        entryPie.add(PieEntry(420f,"2014"))
        entryPie.add(PieEntry(500f,"2015"))
        entryPie.add(PieEntry(530f,"2016"))
        entryPie.add(PieEntry(420f,"2017"))
        entryPie.add(PieEntry(358f,"2018"))


        val pieDataSet : PieDataSet = PieDataSet(entryPie, "Ano")
        pieDataSet.setColors(ColorTemplate.createColors(ColorTemplate.COLORFUL_COLORS))
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 10f


        val pieData : PieData = PieData(pieDataSet)

        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.centerText = "valores"
        pieChart.animateY(2000)
        pieChart.animate()

        return root
    }
}
