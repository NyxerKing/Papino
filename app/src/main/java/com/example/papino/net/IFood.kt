package com.example.papino.net

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IFood {
    @GET(".")
    fun getFood(): Call<ListFood>

    @POST(".")
    fun createOrderFood(
        @Query("id") id: Int,
        @Query("idUser") idUser: Int,
        @Query("useBonus") useBonus: Boolean,
        @Query("address") address: String
    ): Call<ListFood>
}
