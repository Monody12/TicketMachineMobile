package com.example.ticketmachinemobile.network

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class VolleyService(serviceContext: Context) {
    val TAG = "VolleyService: $serviceContext"
    val context = serviceContext

    val baseUrl = "http://192.168.100.105:8080/"
    val queue = Volley.newRequestQueue(serviceContext)

    fun getSimpleDataCallback(apiUrl: String, callback: (ApiResponse<*>, Context) -> Unit) {
        val url = baseUrl + apiUrl
        var success = true

        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener {
                val resp = it.toString()
                Log.d(TAG, resp)
                val response = ApiResponse(code = 200, success = success, data = resp)
                callback(response, this.context)
            },
            Response.ErrorListener {
                val resp = it.toString()
                success = false
                Log.d(TAG, resp)
                val response = ApiResponse(code = 500, success = success, data = resp)
                callback(response, this.context)
            }
        )

        queue.add(stringRequest)
    }


}