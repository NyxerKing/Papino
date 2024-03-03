package com.example.papino.net

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface IFood {
    @GET(".")
    fun getFood(): Call<ListFood>
}