package com.example.ticketmachinemobile.network

import android.content.Context
import com.android.volley.toolbox.Volley

class VolleyApiService(serviceContext: Context) {

    private val TAG = "VolleyService: $serviceContext"
    private val context = serviceContext
    private val volleyService = VolleyService(serviceContext)

    val baseUrl = "http://192.168.100.105:8080/"
    val queue = Volley.newRequestQueue(serviceContext)
    fun getRoutes(callback: (ApiResponse<*>, Context) -> Unit){
        volleyService.getSimpleDataCallback("routes",callback)
    }



}