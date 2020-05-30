package com.example.login.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import java.util.*
import javax.xml.transform.ErrorListener
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    val url= "https://192.168.1.18:56876/graphql"
    val jsonobj = JSONObject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        */
        var  textR = root.findViewById<TextView>(R.id.text_home)

        lateinit var dados1 : JSONObject

        /*NESSE TRECHO FAZ A COMUNICAÇÃO COM O BACKEND*/
        jsonobj.put("query","{ employees { id username } }")
        val queue = Volley.newRequestQueue(this.context)
        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonobj,
            Response.Listener {
                responce -> dados1 = responce//usado para debug : textR.setText("-- " + responce["employees"] + " --")
            }, Response.ErrorListener {error -> //usado para debug : textR.setText("-- " + error.toString() + " --")
                //Toast.makeText(this.context, ""+error.toString() , Toast.LENGTH_SHORT).show()
            })
        queue.add(request)

        val pieChart : PieChart = root.findViewById(R.id.pieChart)

        var entryPie : ArrayList<PieEntry> = ArrayList() // Colocando valores para X e Y
        entryPie.add(PieEntry(420f,"Finalizado"))
        entryPie.add(PieEntry(500f,"Em Andamento"))
        entryPie.add(PieEntry(20f,"Perdas"))

        val pieDataSet : PieDataSet = PieDataSet(entryPie, "Produção")
        pieDataSet.setColors(ColorTemplate.createColors(ColorTemplate.MATERIAL_COLORS))
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 10f

        val pieData : PieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.centerText = "Estatísticas"
        pieChart.animateY(2000)
        pieChart.animate()
        pieChart.setEntryLabelColor(Color.BLACK)

        return root
    }
}
