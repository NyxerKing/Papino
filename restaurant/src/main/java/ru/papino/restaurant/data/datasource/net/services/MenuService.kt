package ru.papino.restaurant.data.datasource.net.services

import retrofit2.Call
import retrofit2.http.GET
import ru.papino.restaurant.data.datasource.net.models.MenuJsonModel

internal interface MenuService {

    @GET("FoodMenu")
    fun getMenu(): Call<MenuJsonModel>
}