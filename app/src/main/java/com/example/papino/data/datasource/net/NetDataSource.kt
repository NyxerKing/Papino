package com.example.papino.data.datasource.net

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetDataSource private constructor() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getRetrofit() = retrofit

    companion object {
        //private const val BASE_URL = "http://192.168.55.7/"
        private const val BASE_URL = "http://nyxerking-001-site1.htempurl.com/"

        fun getInstance() = NetDataSource()
    }
}

