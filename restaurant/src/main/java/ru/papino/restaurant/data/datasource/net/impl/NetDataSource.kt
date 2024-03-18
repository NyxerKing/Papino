package ru.papino.restaurant.data.datasource.net.impl

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.papino.restaurant.core.net.BASE_URL

internal class NetDataSource private constructor() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getRetrofit() = retrofit

    companion object {
        fun getInstance() = NetDataSource()
    }
}