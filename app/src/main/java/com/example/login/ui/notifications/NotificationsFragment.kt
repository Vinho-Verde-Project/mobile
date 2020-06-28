package com.example.login.ui.notifications

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
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import org.json.JSONArray
import org.json.JSONObject
import java.util.Collections.min
import kotlin.math.min

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val globalClass = GlobalClass()
        val url = globalClass.networkIP
        val jsonStock = JSONObject()
        val jsonWine = JSONObject()
        var dadosStock :  JSONArray = JSONArray()
        var dadosStock_Wine :  JSONArray = JSONArray()
        var dadosWine : JSONArray = JSONArray()



        fun buildChart() {

            val barChart : BarChart = root.findViewById(R.id.barChart)
            var entryBar : ArrayList<BarEntry> = ArrayList()
            var nameList = ArrayList<String>()
            var quantidade = 0
            for(x in 0..(dadosWine.length()-1)){

                var auxWineObj = dadosWine.getJSONObject(x)
                var auxArrayStockObj = auxWineObj.getJSONArray("stockWine")
                var auxObjStock : JSONObject

                nameList.add(dadosWine.getJSONObject(x).getJSONObject("category").getString("desc"))

                quantidade = 0
                for (y in 0..(auxArrayStockObj.length()-1)){
                    auxObjStock = auxArrayStockObj.getJSONObject(y)
                    quantidade = quantidade + auxObjStock.getInt("quantity")
                }

                entryBar.add(BarEntry((x+1).toFloat(),quantidade.toFloat()))
            }

            val barDataSet : BarDataSet = BarDataSet(entryBar,"Produtos") // Criando o Formato dataSet pra poder alterar propriedades de visualização do gráfico
            barDataSet.setColors(ColorTemplate.createColors(ColorTemplate.PASTEL_COLORS))
            barDataSet.valueTextSize = 10f
            barDataSet.valueTextColor = Color.BLACK

            val barData = BarData(barDataSet)
            val xAxis : XAxis = barChart.xAxis
            xAxis.setDrawGridLines(false)
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
            xAxis.valueFormatter = IndexAxisValueFormatter(nameList)

            barChart.setFitBars(true)
            barChart.data = barData

            barChart.getDescription().setEnabled(false)

            barChart.animateY(2500) // habilitando animação ao gerar gráfico

        }

        val queue = Volley.newRequestQueue(this.context)

        jsonWine.put("query"," query { wines { id  stockWine { quantity } category { desc }}}")
        val requestWine = JsonObjectRequest(
            Request.Method.POST, url, jsonWine,
            Response.Listener {
                    responce -> dadosWine = responce.getJSONArray("wines")
                if(dadosWine.length() != 0){
                    Log.i("TAG", "Diferetneeee")
                    buildChart()
                }
            }, Response.ErrorListener { error -> //usado para debug : textR.setText("-- " + error.toString() + " --")
                Toast.makeText(this.context, "ERRO Wine: " + error.toString() , Toast.LENGTH_SHORT).show()
            })
        queue.add(requestWine)









        return root
    }
}
