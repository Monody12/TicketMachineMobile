package com.example.ticketmachinemobile.network

import com.example.ticketmachinemobile.data.response.BaseResp
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("routes/")
    fun getCall():Call<BaseResp<Any>>

}