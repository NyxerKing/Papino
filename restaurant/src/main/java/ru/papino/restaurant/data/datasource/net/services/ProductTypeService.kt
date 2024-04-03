package ru.papino.restaurant.data.datasource.net.services

import retrofit2.Call
import retrofit2.http.GET
import ru.papino.restaurant.data.datasource.net.models.ProductTypesJsonModel

internal interface ProductTypeService {

    @GET("/FoodMenu/GetTypeFood")
    fun getProductTypes(): Call<List<ProductTypesJsonModel>>
}