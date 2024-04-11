package ru.papino.restaurant.data.datasource.net.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.papino.restaurant.data.datasource.net.models.OrderJsonModel
import ru.papino.restaurant.domain.models.OrderRequestModel

internal interface OrdersService {

    @GET("/FoodMenu/GetOrderUser")
    fun getOrders(@Query("idUser") userId: Long): Call<List<OrderJsonModel>>

    @POST("/FoodMenu/CreateOrder")
    fun createOrder(@Body order: OrderRequestModel): Call<OrderJsonModel>
}