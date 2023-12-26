package com.example.ticketmachinemobile.network

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class VolleyService(context: Context) {
    val TAG = "VolleyService: $context"

    val baseUrl = "http://192.168.100.105:8080/"
    val queue = Volley.newRequestQueue(context)
    fun getSimpleData(): ApiResponse<String> {
        val url = baseUrl + "routes"
        var resp: String? = null
        var success = true
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener {
                resp = it.toString()
                Log.d(TAG,resp.toString())
            },
            Response.ErrorListener {
                resp = it.toString()
                success = false
                Log.d(TAG,resp.toString())
            }
        )
        queue.add(stringRequest)
        return ApiResponse(code = 200, success = success, data = resp)
    }

}