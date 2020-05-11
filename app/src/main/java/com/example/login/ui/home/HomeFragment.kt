package com.example.login.ui.home

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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

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
