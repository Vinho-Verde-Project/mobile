package com.example.login.ui.dashboard

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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.json.JSONObject

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    val url= "https://192.168.1.18:56876/graphql"
    val jsonobj = JSONObject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        */
        lateinit var dados2 : JSONObject

        /*NESSE TRECHO FAZ A COMUNICAÇÃO COM O BACKEND*/
        jsonobj.put("query","{ employees { id username } }")
        val queue = Volley.newRequestQueue(this.context)
        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonobj,
            Response.Listener {
                    responce -> dados2 = responce//usado para debug : textR.setText("-- " + responce["employees"] + " --")
            }, Response.ErrorListener { error -> //usado para debug : textR.setText("-- " + error.toString() + " --")
               // Toast.makeText(this.context, ""+error.toString() , Toast.LENGTH_SHORT).show()
            })
        queue.add(request)



        val lineChart : LineChart = root.findViewById(R.id.lineChart)

        val entryLine : ArrayList<Entry> = ArrayList()
        entryLine.add(Entry(2013f, 350000f))
        entryLine.add(Entry(2014f, 380000f))
        entryLine.add(Entry(2015f, 400000f))
        entryLine.add(Entry(2016f, 450000f))
        entryLine.add(Entry(2017f, 500000f))
        entryLine.add(Entry(2018f, 520000f))
        entryLine.add(Entry(2019f, 580000f))
        entryLine.add(Entry(2020f, 650000f))

        val lineDataSet: LineDataSet = LineDataSet(entryLine, "Produção Anual")
        lineDataSet.setColor(Color.RED)
        lineChart.getDescription().setEnabled(false)
        lineDataSet.valueTextColor = Color.BLACK
        lineDataSet.valueTextSize = 10f

        val lineData : LineData = LineData(lineDataSet)

        lineChart.data = lineData
        lineChart.animateX(2000)


        return root
    }
}
