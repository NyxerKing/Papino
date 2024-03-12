package com.example.papino.data.repository

import com.example.papino.data.datasource.local.LocalDataSource
import com.example.papino.data.datasource.net.MenuService
import com.example.papino.data.datasource.net.NetDataSource
import com.example.papino.net.ListFood
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuRepository(
    private val localDS: LocalDataSource,
    private val netDS: NetDataSource
) {

    fun getMenu(callResponse: (ListFood) -> Unit, callError: (ListFood) -> Unit) {
        val menuService = netDS.getRetrofit().create(MenuService::class.java)
        menuService.getMenu().enqueue(object : Callback<ListFood> {
            override fun onResponse(call: Call<ListFood>, response: Response<ListFood>) {
                callResponse(
                    response.body() ?: Gson().fromJson(
                        localDS.getData(),
                        ListFood::class.java
                    )
                )
            }

            override fun onFailure(call: Call<ListFood>, t: Throwable) {
                callError(Gson().fromJson(localDS.getData(), ListFood::class.java))
            }
        })
    }
}