package com.example.papino.presentation.regestration.controlles

import com.example.papino.net.IUsers
import com.example.papino.net.ListUser
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit


class ControllerUser(val callBack: (ListUser, String) -> Unit , val callBackError: (t: Throwable) -> Unit) : Callback<ListUser>  {
    private var iUserGet: IUsers? = null
    lateinit var messageCallBack : String
    var errorMessage : String? = ""

    fun start(
        surname: String, name: String, telephoneNumber: String, password: String,
        bonus: String, registration: Boolean
    )  : String
    {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        iUserGet = retrofit.create(IUsers::class.java)
        var call: Call<ListUser>? = null
        if (!registration) {
            call = getUser()?.getUser(telephoneNumber, password)
        } else {
            call = createUser()?.createUser(surname, name, telephoneNumber, password, bonus)
        }
        call?.enqueue(this)
        return errorMessage!!
    }

    override fun onResponse(call: Call<ListUser>, response: Response<ListUser>) {
        if (response.isSuccessful()) {
            val changesList = response.body()
            messageCallBack = response.message()
            if (changesList != null) callBack.invoke(changesList, messageCallBack)
        } else {
            var errorText = response.body().toString()
            messageCallBack = errorText
        }
    }


    override fun onFailure(call: Call<ListUser>, t: Throwable)  {
        callBackError.invoke(t)
    }

    fun getUser(): IUsers? {
        return iUserGet
    }

    fun createUser(): IUsers? {
        return iUserGet
    }

    companion object {
        //const val BASE_URL = "http://192.168.55.7/"
        const val BASE_URL = "http://nyxerapi.somee.com/"
    }
}

// Рабочее с вводом пароля

/*

class ControllerUser(val callBack: (ListUser)->Unit): Callback<ListUser>
{
    private var iUserGet: IUsers? = null
    fun start(telephoneNumber : String)
    {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        iUserGet = retrofit.create(IUsers::class.java)
        val call = getUser()?.getUser(telephoneNumber)
        call?.enqueue(this)
    }

    override fun onResponse(call: Call<ListUser>, response: Response<ListUser>)
    {
        if (response.isSuccessful()) {
            val changesList = response.body()
            if (changesList != null)  callBack.invoke(changesList)

        }
        else {
            System.out.println(response.errorBody())
        }
    }

    override fun onFailure(call: Call<ListUser>, t: Throwable)
    {
        t.printStackTrace()
    }

    fun getUser(): IUsers?
    {
        return iUserGet
    }

 */