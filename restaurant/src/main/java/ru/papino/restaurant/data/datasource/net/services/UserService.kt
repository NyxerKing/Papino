package ru.papino.restaurant.data.datasource.net.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.papino.restaurant.data.datasource.net.models.UserJsonModel

internal interface UserService {

    @POST("/User/CreateUser")
    fun createUser(
        @Query("name") secondName: String,
        @Query("surname") firstName: String,
        @Query("telephoneNumber") phone: String,
        @Query("password") password: String,
        @Query("bonus") bonus: Long,
        @Query("address") address: String,
    ): Call<UserJsonModel>

    @GET("/User")
    fun getUser(
        @Query("telephoneNumber") phone: String,
        @Query("password") password: String
    ): Call<UserJsonModel>

    @GET("/User")
    fun getUserByToken(
        @Query("token") token: String
    ): Call<UserJsonModel>

    @POST("/update")
    fun updateUser(
        @Query("name") secondName: String?,
        @Query("surname") firstName: String?,
        @Query("address") address: String?
    ): Call<UserJsonModel>
}