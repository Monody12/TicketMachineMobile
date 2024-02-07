package com.example.ticketmachinemobile.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

object RetrofitManger {

    var mApi: ApiService? = null

    private const val CONNECTION_TIME_OUT = 10L
    private const val READ_TIME_OUT = 10L

    private var API_URL = "http://192.168.100.169:8080/"
//    private var API_URL = "http://192.168.0.4:8080/"

    fun getApiService(): ApiService {
        if (mApi == null) {
            synchronized(this) {
                if (mApi == null) {
                    val okHttpClient =
                        buildOkHttpClient()
                    mApi =
                        buildRetrofit(
                            API_URL,
                            okHttpClient
                        ).create(ApiService::class.java)
                }
            }
        }
        return mApi!!
    }

    private fun buildOkHttpClient(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .proxy(Proxy.NO_PROXY)
    }

    private fun buildRetrofit(baseUrl: String, builder: OkHttpClient.Builder): Retrofit {
        val client = builder.build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
    }
}
