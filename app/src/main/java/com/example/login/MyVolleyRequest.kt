package com.example.login

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MyVolleyRequest {
    private var mRequestQueue:RequestQueue?=null
    private var context:Context?=null
    private var iVolley:IVolley?=null

    val requestQueue:RequestQueue
        get(){
            if (mRequestQueue === null)
                mRequestQueue = Volley.newRequestQueue(context!!.applicationContext)
            return  mRequestQueue!!
        }

    private  constructor(context: Context, iVolley: IVolley){
        this.context = context
        this.iVolley = iVolley
        mRequestQueue = requestQueue
    }

    private  constructor(context: Context){
        this.context = context
        mRequestQueue = requestQueue
    }

    fun <T> addToRequestQueue(req:Request<T>){
        requestQueue.add(req)
    }

    //GET METHOD
    fun getRequest(url: String){
        val getRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response -> iVolley!!.onResponse(response.toString())
        }, Response.ErrorListener { error -> iVolley!!.onResponse(error.message!!) } )
        addToRequestQueue(getRequest)
    }

    //POST METHOD
    fun postRequest(url: String){
        val postResquest = object:StringRequest(Request.Method.POST, url,
            Response.Listener { response ->   iVolley!!.onResponse(response.toString())}, Response.ErrorListener { error -> iVolley!!.onResponse(error.message!!) })
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["email"] = "this is value posted from field email in Android App"
                params["password"] = "this is value posted from field password in Android App"
                return params
            }
        }
        addToRequestQueue(postResquest)
    }

    companion object{
        private var mInstance : MyVolleyRequest?=null
        @Synchronized
        fun getInstance (context: Context): MyVolleyRequest{
            if (mInstance === null)
                mInstance = MyVolleyRequest(context)
            return mInstance!!
        }
        @Synchronized
        fun getInstance (context: Context, iVolley: IVolley): MyVolleyRequest{
            if (mInstance === null)
                mInstance = MyVolleyRequest(context, iVolley)
            return mInstance!!
        }
    }

}