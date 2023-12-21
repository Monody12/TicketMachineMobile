package com.example.ticketmachinemobile.network

import android.util.Log
import com.example.ticketmachinemobile.data.response.BaseResp
import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class RetrofitService @Inject constructor() {

    companion object {
        val TAG: String = "RetrofitService"
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.100.105:8080/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        suspend fun getInfo(): BaseResp<Any> {
            return suspendCancellableCoroutine { continuation ->
                val call = apiService.getCall()
                call.enqueue(object : Callback<BaseResp<Any>> {
                    override fun onResponse(call: Call<BaseResp<Any>>, response: Response<BaseResp<Any>>) {
                        Log.i(TAG, "请求成功：${response.body().toString()}")
                        continuation.resume(response.body()!!)
                    }

                    override fun onFailure(call: Call<BaseResp<Any>>, t: Throwable) {
                        Log.i(TAG, "请求失败")
                        continuation.resumeWithException(t)
                    }
                })
            }
        }

    }
}