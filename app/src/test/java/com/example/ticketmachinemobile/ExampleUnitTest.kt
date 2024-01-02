package com.example.ticketmachinemobile

import com.example.ticketmachinemobile.data.response.BaseResp
import com.example.ticketmachinemobile.network.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val TAG = "UnitTest"

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

//    @Test
//    fun retrofit_test(){
//        val retrofit: Retrofit = Retrofit.Builder()
//            .baseUrl("https://vps.dluserver.cn/")
//            .addConverterFactory(GsonConverterFactory.create(Gson()))
//            .build()
//
//        val apiService = retrofit.create(ApiService::class.java)
//
//        fun getInfo() : BaseResp<out Any?>? {
//            val call = apiService.getCall()
//            var resp : BaseResp<out Any?>? = null
//            call.enqueue(object : Callback<BaseResp<Any>> {
//                override fun onResponse(
//                    call: Call<BaseResp<Any>>,
//                    response: Response<BaseResp<Any>>
//                ) {
//                    Log.i(RetrofitService.TAG, "请求成功：${response.body().toString()}")
//                    resp = response.body()
//                }
//
//                override fun onFailure(call: Call<BaseResp<Any>>, t: Throwable) {
//                    Log.i(RetrofitService.TAG, "请求失败")
//                }
//            })
//            return resp
//        }
//
//        val resp = getInfo()
//        println(resp)
//    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun retrofit_test() = runBlocking {
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
//                        Log.i(RetrofitService.TAG, "请求成功：${response.body().toString()}")
                        continuation.resume(response.body()!!)
                    }

                    override fun onFailure(call: Call<BaseResp<Any>>, t: Throwable) {
//                        Log.i(RetrofitService.TAG, "请求失败")
                        continuation.resumeWithException(t)
                    }
                })
            }
        }

        val resp = getInfo()
        println(resp)
    }


}