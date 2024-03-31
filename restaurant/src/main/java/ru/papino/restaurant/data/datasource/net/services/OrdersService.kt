package ru.papino.restaurant.data.datasource.net.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.papino.restaurant.data.datasource.net.models.OrderJsonModel
import ru.papino.restaurant.domain.repository.models.OrderRequestModel

internal interface OrdersService {

    // TODO параметры неизвестны
    @GET("/orders")
    fun getOrders(): Call<List<OrderJsonModel>>

    @POST("/createOrder")
    fun createOrder(@Body order: OrderRequestModel): Call<OrderJsonModel>
}