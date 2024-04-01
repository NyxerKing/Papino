package ru.papino.restaurant.data.datasource.net.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.papino.restaurant.data.datasource.net.models.OrderJsonModel
import ru.papino.restaurant.domain.repository.models.OrderRequestModel

internal interface OrdersService {

    @GET("/FoodMenu/GetOrderUser")
    fun getOrders(): Call<List<OrderJsonModel>>

    //{"group":[{"id":9,"isApplyBonus":false,"userId":10,"dateOrders":"2024-03-31T09:37:22.997","addressOrder":"dimitrovgrad","statusOrder":"Создан","summOrder":308000,"summDiscount":30800,"summOrderWithDiscount":277200}]}
    @POST("/FoodMenu/CreateOrder")
    fun createOrder(@Body order: OrderRequestModel): Call<OrderJsonModel>
}