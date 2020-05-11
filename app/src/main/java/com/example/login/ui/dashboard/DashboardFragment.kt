package com.example.login.ui.dashboard

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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val lineChart : LineChart = root.findViewById(R.id.lineChart)

        val entryLine : ArrayList<Entry> = ArrayList()
        entryLine.add(Entry(1f, 5f))
        entryLine.add(Entry(2f, 3f))
        entryLine.add(Entry(3f, 9f))
        entryLine.add(Entry(4f, 7f))
        entryLine.add(Entry(5f, 2f))

        val lineDataSet: LineDataSet = LineDataSet(entryLine, "rand")
        lineDataSet.setColor(Color.RED)
        lineDataSet.valueTextColor = Color.BLACK
        lineDataSet.valueTextSize = 10f

        val lineData : LineData = LineData(lineDataSet)

        lineChart.data = lineData
        lineChart.animateX(2000)


        return root
    }
}
