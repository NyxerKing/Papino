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
    fun getUser(
        @Query("telephoneNumber") telephoneNumber: String,
        @Query("password") password: String
    ): Call<ListUser>

    @POST("/User/?=")
    fun createUser(
        @Query("surname") surname: String,
        @Query("name") name: String,
        @Query("telephoneNumber") telephoneNumber: String,
        @Query("password") password: String,
        @Query("bonus") bonus: String
        //@Query("token") token: String,
        //@Query("dateCreatedtoken") dateCreatedtoken: String
    ): Call<ListUser>

}


// http://192.168.55.7/User/?surname=gdcgjjj&name=llllllll&telephoneNumber=33366699&password=gfdbkjfedhjwsd&bonus=0&token=5684751b-5bc3-4ed7-a90a-66c00840720a