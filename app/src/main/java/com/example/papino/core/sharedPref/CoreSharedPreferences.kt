package com.example.papino.core.sharedPref

import android.content.Context
import com.example.papino.presentation.menu.models.PackFoodBaskedModel
import com.google.gson.Gson

class CoreSharedPreferences(private val context: Context) {

    private val basketSharedPreferences =
        context.getSharedPreferences(SharedKeys.BASKED_DATA_KEY, Context.MODE_PRIVATE)

    fun getBasket(): PackFoodBaskedModel? = Gson().fromJson(
        basketSharedPreferences.getString(SharedKeys.BASKED_ITEM_KEY, ""),
        PackFoodBaskedModel::class.java
    )
}