package ru.papino.restaurant.data.datasource.net.services

import retrofit2.Call
import retrofit2.http.GET
import ru.papino.restaurant.data.datasource.net.models.AboutJsonModel

internal interface AboutService {

    @GET("/Organization/GetOrganizationInfo")
    fun getAbout(): Call<AboutJsonModel>
}