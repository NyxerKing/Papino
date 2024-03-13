package ru.papino.restaurant.extensions

import com.google.gson.Gson
import ru.papino.restaurant.presentation.basket.models.BasketUIModel

internal fun BasketUIModel.toJson() = Gson().toJson(this)