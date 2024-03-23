package ru.papino.restaurant.data.datasource.net.services

import retrofit2.Call
import retrofit2.http.GET
import ru.papino.restaurant.data.datasource.net.models.OrderJsonModel

internal interface OrdersService {

    // TODO параметры неизвестны
    @GET("/orders")
    fun getOrders(): Call<List<OrderJsonModel>>
}