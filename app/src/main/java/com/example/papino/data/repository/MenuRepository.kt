package com.example.papino.data.repository

import com.example.papino.data.datasource.local.LocalDataSource
import com.example.papino.data.datasource.net.MenuService
import com.example.papino.data.datasource.net.NetDataSource
import com.example.papino.net.ListFood
import com.google.gson.Gson

class MenuRepository(
    private val localDS: LocalDataSource,
    private val netDS: NetDataSource
) {

    fun getMenu(): ListFood {
        val menuService = netDS.getRetrofit().create(MenuService::class.java)
        return try {
            val menuNet = menuService.getMenu().execute()
            menuNet.body() ?: Gson().fromJson(localDS.getData(), ListFood::class.java)
        } catch (ex: Exception) {
            Gson().fromJson(localDS.getData(), ListFood::class.java)
        }
    }
}