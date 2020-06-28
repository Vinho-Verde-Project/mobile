package com.example.login.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.login.GlobalClass
import com.example.login.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.json.JSONArray
import org.json.JSONObject

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    val jsonStock = JSONObject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val globalClass = GlobalClass()
        val url = globalClass.networkIP
        val entryLine : ArrayList<Entry> = ArrayList()

        fun buildChart(dadosStock: JSONArray) {

            var auxArrayObj : JSONArray
            var auxObj : JSONObject
            var cont = 0


            for (x in 0..(dadosStock.length()-1)){
                Log.i("TAG", x.toString())
                auxArrayObj = dadosStock.getJSONObject(x).getJSONArray("stockWine")
                for( y in 0..(auxArrayObj.length()-1)){
                    auxObj = auxArrayObj.getJSONObject(y)
                    entryLine.add(Entry(cont.toFloat(), auxObj.getInt("quantity").toFloat()))
                    cont++
                }
            }

            val lineChart : LineChart = root.findViewById(R.id.lineChart)

            val lineDataSet: LineDataSet = LineDataSet(entryLine, "Produção Anual")
            lineDataSet.setColor(Color.RED)
            lineChart.getDescription().setEnabled(false)
            lineDataSet.valueTextColor = Color.BLACK
            lineDataSet.valueTextSize = 10f

            val lineData : LineData = LineData(lineDataSet)

            lineChart.data = lineData
            lineChart.animateX(2000)
        }

        jsonStock.put("query"," query { stocks { id title  stockWine{ quantity entryDate }}}")

        val queue = Volley.newRequestQueue(this.context)
        var dadosStock : JSONArray = JSONArray()
            val requestStocks = JsonObjectRequest(
                Request.Method.POST, url, jsonStock,
                Response.Listener {
                        responce ->  dadosStock = responce.getJSONArray("stocks")
                    if(dadosStock.length() != 0){
                        buildChart(dadosStock)
                    }

                }, Response.ErrorListener { error ->
                    Toast.makeText(this.context, "ERRO Stocks: " + error.toString() , Toast.LENGTH_SHORT).show()
                })
            queue.add(requestStocks)
        return root
    }
}
