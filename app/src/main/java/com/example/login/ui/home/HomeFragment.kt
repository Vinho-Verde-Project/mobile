package com.example.login.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.login.*
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    val jsonStock = JSONObject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val globalClass = GlobalClass()
        val url = globalClass.networkIP

        lateinit var dados1 : JSONObject

        var textHome = root.findViewById<TextView>(R.id.text_home)
        val pieChart : PieChart = root.findViewById(R.id.pieChart)
        var pieDataList = ArrayList<PieData>()
        var index = 0
        var pieDataSetList = ArrayList<PieDataSet>()
        var pieEntryList = ArrayList<ListEntryPie>()

        fun geraGraficos(pieEntryList: ArrayList<ListEntryPie>) {

            for (x in 0..(pieEntryList.size-1)){
                pieDataSetList.add(PieDataSet(pieEntryList.get(x).list, pieEntryList.get(x).name))
                pieDataSetList.get(x).setColors(ColorTemplate.createColors(ColorTemplate.MATERIAL_COLORS))
                pieDataSetList.get(x).valueTextColor = Color.BLACK
                pieDataSetList.get(x).valueTextSize = 10f
            }

            for(x in 0..pieDataSetList.size-1 ) {
                pieDataList.add(PieData(pieDataSetList.get(x)))
            }

            textHome.setText("Lotação "+pieEntryList.get(index).name)
            pieChart.data = pieDataList.get(index)
            pieChart.description.isEnabled = false
            pieChart.centerText = "Estatísticas"
            pieChart.animateY(2000)
            pieChart.animate()
            pieChart.setEntryLabelColor(Color.BLACK)
        }

        fun mudaDados(){
            if (index+1 <= pieDataList.size-1){
                index = index + 1
                Log.i("TAG", "list " + pieDataList.get(index).toString() )
                pieChart.data = pieDataList.get(index)
                textHome.setText("Lotação "+pieEntryList.get(index).name)
            }else{
                index = 0
                textHome.setText("Lotação "+pieEntryList.get(index).name)
                pieChart.data = pieDataList.get(index)
            }

        }


        fun buildChart(dadosStock: JSONArray) {

            var flagAdd = 1
            var wareHouseList = ArrayList<WareHouse>() ;
            var jsonArrayAux : JSONArray;
            var jsonAux : JSONObject
            var stockWineArray = ArrayList<StockWine>()

            if(dadosStock.length() != 0){
                for (x in 0..(dadosStock.length()-1)){

                        jsonArrayAux = dadosStock.getJSONObject(x).getJSONArray("stockWine")
                        stockWineArray.clear()
                        for(y in 0..(jsonArrayAux.length()-1)){

                            jsonAux = jsonArrayAux.getJSONObject(y)
                            stockWineArray.add(StockWine(jsonAux.getInt("quantity").toFloat(), jsonAux.getInt("stockId"), jsonAux.getInt("wineId"),
                                                         jsonAux.getString("unit"), jsonAux.getInt("employeeId"), jsonAux.getString("entryDate")))

                        }

                        var quantity = 0
                        for (v in 0..(stockWineArray.size-1)){
                            Log.i("TAG", stockWineArray.get(v).quantity.toString() + "  " + v.toString())
                            quantity = quantity + stockWineArray.get(v).quantity.toInt()
                        }
                        wareHouseList.add(WareHouse(dadosStock.getJSONObject(x).getString("title"), dadosStock.getJSONObject(x).getInt("id"),quantity))
                    }
                }


            for (x in 0..(wareHouseList.size-1)){
                pieEntryList.add(ListEntryPie(wareHouseList.get(x).title,wareHouseList.get(x).quantity.toFloat()))
            }

            geraGraficos(pieEntryList)
        }

        val queue = Volley.newRequestQueue(this.context)
        var dadosStock : JSONArray = JSONArray()


        jsonStock.put("query"," query { stocks { id title  stockWine{ quantity stockId wineId unit employeeId entryDate }}}")
        val requestStocks = JsonObjectRequest(
            Request.Method.POST, url, jsonStock,
            Response.Listener { responce ->  dadosStock = responce.getJSONArray("stocks")//usado para debug : textR.setText("-- " + responce["employees"] + " --")
                //Toast.makeText(this.context, "Recuperou dados de Stocks" , Toast.LENGTH_SHORT).show()
                Log.i("TAG",responce.toString())
                Log.i("TAG",dadosStock.toString())
                Log.i("TAG",dadosStock.getJSONObject(0).toString())
                Log.i("TAG",dadosStock.length().toString())
                if(dadosStock.length() != 0){
                    buildChart(dadosStock)
                }

            }, Response.ErrorListener { error -> //usado para debug : textR.setText("-- " + error.toString() + " --")
                Toast.makeText(this.context, "ERRO Stocks: " + error.toString() , Toast.LENGTH_SHORT).show()
            })
        queue.add(requestStocks)


        var buttonNext = root.findViewById<Button>(R.id.button_next_home)

        buttonNext.setOnClickListener({
            mudaDados()
        })



        return root
    }
}
