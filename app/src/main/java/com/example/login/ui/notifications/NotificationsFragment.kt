package com.example.login.ui.notifications

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
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import org.json.JSONObject

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    //Url  Para acesso a API
    val url= "https://192.168.1.18:56876/graphql"
    val jsonobj = JSONObject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        lateinit var dados3 : JSONObject

        /*NESSE TRECHO FAZ A COMUNICAÇÃO COM O BACKEND*/
        jsonobj.put("query","{ product }")
        val queue = Volley.newRequestQueue(this.context)
        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonobj,
            Response.Listener {
                    responce -> dados3 = responce//usado para debug : textR.setText("-- " + responce["employees"] + " --")
            }, Response.ErrorListener { error -> //usado para debug : textR.setText("-- " + error.toString() + " --")
               // Toast.makeText(this.context, ""+error.toString() , Toast.LENGTH_SHORT).show()
            })
        queue.add(request)



        val barChart : BarChart = root.findViewById(R.id.barChart) // Selecionando o gráfico na interface do aplicativo

        var entryBar : ArrayList<BarEntry> = ArrayList() // Colocando valores para X e Y
        entryBar.add(BarEntry(1f,420f))
        entryBar.add(BarEntry(2f,500f))
        entryBar.add(BarEntry(3f,530f))
        entryBar.add(BarEntry(4f,420f))
        entryBar.add(BarEntry(5f,358f))


        val barDataSet : BarDataSet = BarDataSet(entryBar,"Produtos") // Criando o Formato dataSet pra poder alterar propriedades de visualização do gráfico
        barDataSet.setColors(ColorTemplate.createColors(ColorTemplate.PASTEL_COLORS))
        barDataSet.valueTextSize = 10f
        barDataSet.valueTextColor = Color.BLACK

        val barData = BarData(barDataSet)

        /*
        val label = ArrayList<String>()
        label.add("A")
        label.add("B")
        label.add("C")
        label.add("D")
        label.add("E")
        label.add("F")
        */
        val xAxis : XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        //xAxis.granularity=1f
        //barChart.xAxis.valueFormatter = IndexAxisValueFormatter(label)
        /*
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return xLabel.get((int)value);
        }
    });

        */
        barChart.setFitBars(true) // Mudando propriedades da janela do gráfico
        barChart.data = barData
        barChart.getDescription().setEnabled(false)
        barChart.getDescription().setText("Bar Example")
        barChart.getDescription().textColor = Color.GREEN
        barChart.animateY(2500) // habilitando animação ao gerar gráfico


        return root
    }
}
