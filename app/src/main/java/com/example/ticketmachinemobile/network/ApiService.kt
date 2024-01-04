package com.example.ticketmachinemobile.network

import com.example.ticketmachinemobile.network.resp.ShiftInfo
import com.example.ticketmachinemobile.data.response.BaseResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("routes/")
    fun getCall():Call<BaseResp<Any>>

    @GET("ticket/check")
    suspend fun getCheckTicket(
        @Query("date") date:String,
        @Query("checkType") checkType:Int
    ) : ApiResponse<MutableList<ShiftInfo>>

    @GET("ticket/sale")
    suspend fun getSaleTicket(
        @Query("date") date:String,
        @Query("checkType") checkType:Int
    ) : ApiResponse<MutableList<ShiftInfo>>

}