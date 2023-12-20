package com.example.ticketmachinemobile.network

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

class RetrofitService @Inject constructor() {

    companion object{
         val retrofit: Retrofit = Retrofit.Builder()
             .baseUrl("https://vps.dluserver.cn/")
             .addConverterFactory(GsonConverterFactory.create(Gson()))
             .build()

        val apiService  = retrofit.create(ApiService::class.java)

        fun getInfo(){
            val call = apiService.getCall()

        }

    }
}