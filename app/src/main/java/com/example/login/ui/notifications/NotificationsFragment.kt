package com.example.login.ui.notifications

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.login.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

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
        val textView: TextView = root.findViewById(R.id.text_notifications)
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val barChart : BarChart = root.findViewById(R.id.barChart) // Selecionando o gráfico na interface do aplicativo

        var entryBar : ArrayList<BarEntry> = ArrayList() // Colocando valores para X e Y
        entryBar.add(BarEntry(2014f,420f))
        entryBar.add(BarEntry(2015f,500f))
        entryBar.add(BarEntry(2016f,530f))
        entryBar.add(BarEntry(2017f,420f))
        entryBar.add(BarEntry(2018f,358f))
        entryBar.add(BarEntry(2019f,820f))
        entryBar.add(BarEntry(2020f,645f))

        val barDataSet : BarDataSet = BarDataSet(entryBar,"Unidades") // Criando o Formato dataSet pra poder alterar propriedades de visualização do gráfico
        barDataSet.setColors(ColorTemplate.createColors(ColorTemplate.MATERIAL_COLORS))
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

        val xAxis : XAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(label)
        */
        barChart.setFitBars(true) // Mudando propriedades da janela do gráfico
        barChart.data = barData
        barChart.getDescription().setText("Bar Example")
        barChart.getDescription().textColor = Color.GREEN
        barChart.animateY(2500) // habilitando animação ao gerar gráfico


        return root
    }
}
