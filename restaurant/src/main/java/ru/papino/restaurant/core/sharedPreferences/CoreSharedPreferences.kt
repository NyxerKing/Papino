package ru.papino.restaurant.core.sharedPreferences

import android.content.Context

class CoreSharedPreferences(context: Context) {

    private val basketSharedPreferences =
        context.getSharedPreferences(BASKED_DATA_KEY, Context.MODE_PRIVATE)

    private val tokenSharedPreferences =
        context.getSharedPreferences(TOKEN_DATA_KEY, Context.MODE_PRIVATE)

    fun getBasket() = basketSharedPreferences.getString(BASKED_ITEM_KEY, "")

    fun setBasket(data: String) {
        basketSharedPreferences.edit().run {
            putString(BASKED_ITEM_KEY, data)
            commit()
        }
    }

    fun getToken(): String? = tokenSharedPreferences.getString(TOKEN_ITEM_KEY, "")

    fun setToken(token: String) {
        tokenSharedPreferences.edit().run {
            putString(TOKEN_ITEM_KEY, token)
            commit()
        }
    }

    private companion object {
        const val BASKED_DATA_KEY = "BASKED_DATA_KEY"
        const val BASKED_ITEM_KEY = "BASKED_ITEM_KEY"
        const val TOKEN_DATA_KEY = "USER_DATA_KEY"
        const val TOKEN_ITEM_KEY = "USER_ITEM_KEY"
    }
}