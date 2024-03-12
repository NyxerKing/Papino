package com.example.papino.data.datasource.net

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
        private const val BASE_URL = "http://nyxerapi.somee.com/"

        fun getInstance() = NetDataSource()
    }
}

