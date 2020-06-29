package com.example.login.ui.tasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.login.GlobalClass
import com.example.login.R
import com.example.login.Tasks
import com.example.login.TasksListAdapter
import org.json.JSONArray
import org.json.JSONObject

class TasksFragment : Fragment() {

    private lateinit var tasksViewModel: TasksViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tasksViewModel =
            ViewModelProviders.of(this).get(TasksViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_tasks, container, false)
        val globalClass = GlobalClass()
        val url = globalClass.networkIP

        val jsonTasks = JSONObject()
        var dadosTasks :  JSONArray = JSONArray()

        var listView  = root.findViewById<ListView>(R.id.listView)

        fun createTasks(){
            var listTasks = ArrayList<Tasks>()
            listTasks.add(Tasks(0,"12/20/2020", "12/21/2020", "Active"))
            listTasks.add(Tasks(1,"12/21/2020", "12/22/2020", "Active"))
            listTasks.add(Tasks(2,"12/22/2020", "12/23/2020", "DONE"))
            listTasks.add(Tasks(3,"12/23/2020", "12/24/2020", "DONE"))
            listTasks.add(Tasks(4,"12/24/2020", "12/25/2020", "Active"))
            listTasks.add(Tasks(5,"12/25/2020", "12/26/2020", "DONE"))

            var adapter = TasksListAdapter(this.context, R.layout.adapter_view, listTasks)
            listView.adapter = adapter

        }

        val queue = Volley.newRequestQueue(this.context)

        jsonTasks.put("query"," query { wines { id  stockWine { quantity } category { desc }}}")
        val requestWine = JsonObjectRequest(
            Request.Method.POST, url, jsonTasks,
            Response.Listener {
                    responce -> dadosTasks = responce.getJSONArray("wines")
                if(dadosTasks.length() != 0){
                    createTasks()
                    Log.i("TAG", "Diferetneeee")
                }
            }, Response.ErrorListener { error -> //usado para debug : textR.setText("-- " + error.toString() + " --")
                createTasks()
                Toast.makeText(this.context, "ERRO Tasks: " + error.toString() , Toast.LENGTH_SHORT).show()
            })
        queue.add(requestWine)

        return root
    }
}
