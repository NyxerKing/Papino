package com.example.papino.data.datasource.net

import com.example.papino.net.ListFood
import retrofit2.Call
import retrofit2.http.GET

interface MenuService {

    @GET("FoodMenu")
    fun getMenu(): Call<ListFood>
}