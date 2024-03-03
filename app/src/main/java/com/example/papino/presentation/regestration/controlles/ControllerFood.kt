package com.example.papino.presentation.regestration.controlles

import com.example.papino.net.IFood
import com.example.papino.net.ListFood
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ControllerFood(val callBack: (ListFood)->Unit): Callback<ListFood> {

    private var iFoodTest: IFood? = null

    fun start() {

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        iFoodTest = retrofit.create(IFood::class.java)
        val call = getApi()?.getFood()
        call?.enqueue(this)
    }

    override fun onResponse(call: Call<ListFood>, response: Response<ListFood>) {
        if (response.isSuccessful())
        {
            val listFoodParam : ListFood? = response.body()
            //val listResult : ListFood? = listFoodParam
            if (listFoodParam != null)  callBack.invoke(listFoodParam)
        }
        else
        {
            System.out.println(response.errorBody())
        }
    }

    override fun onFailure(call: Call<ListFood>, t: Throwable)
    {
        t.printStackTrace()
    }

    fun getApi(): IFood? {
        return iFoodTest
    }

    companion object {const val BASE_URL = "http://192.168.55.7/FoodMenu/"}

}


/*private fun <T> Call<T>?.enqueue(callback: Callback<ListFood>) {
    TODO("Not yet implemented")
}*/
