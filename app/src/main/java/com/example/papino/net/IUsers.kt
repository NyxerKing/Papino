package com.example.papino.net

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface IUsers {
   /* @GET("/User/?=")
    fun getUser(@Query("telephoneNumber")  telephoneNumber : String): Call<ListUser>*/

    @GET("/User/?=")
    fun getUser(@Query("telephoneNumber")  telephoneNumber : String, @Query("password")  password : String): Call<ListUser>

    @POST("/User/?=")
    fun postUser(@Query("surname")  surname : String,
                 @Query("name")  name : String,
                 @Query("telephoneNumber")  telephoneNumber : String,
                 @Query("password")  password : String,): Call<ListUser>

}

// https://localhost:44336/User?telephoneNumber=123&password=3125510

// https://localhost:44336/User?surname=ihg&name=iughuih&telephoneNumber=9879&password=lkhtf6545