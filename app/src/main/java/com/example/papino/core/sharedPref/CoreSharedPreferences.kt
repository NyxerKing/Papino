package com.example.papino.core.sharedPref

import android.content.Context
import com.example.papino.presentation.menu.models.PackFoodBaskedModel
import com.google.gson.Gson

class CoreSharedPreferences(context: Context) {

    private val basketSharedPreferences =
        context.getSharedPreferences(SharedKeys.BASKED_DATA_KEY, Context.MODE_PRIVATE)

    private val tokenSharedPreferences =
        context.getSharedPreferences(SharedKeys.TOKEN_DATA_KEY, Context.MODE_PRIVATE)

    fun getBasket(): PackFoodBaskedModel? = Gson().fromJson(
        basketSharedPreferences.getString(SharedKeys.BASKED_ITEM_KEY, ""),
        PackFoodBaskedModel::class.java
    )

    fun setBasket(data: PackFoodBaskedModel) {
        basketSharedPreferences.edit().run {
            putString(SharedKeys.BASKED_ITEM_KEY, Gson().toJson(data))
            commit()
        }
    }

    fun getToken(): String? = tokenSharedPreferences.getString(SharedKeys.TOKEN_ITEM_KEY, "")

    fun setToken(token: String) {
        tokenSharedPreferences.edit().run {
            putString(SharedKeys.TOKEN_ITEM_KEY, token)
            commit()
        }
    }
}