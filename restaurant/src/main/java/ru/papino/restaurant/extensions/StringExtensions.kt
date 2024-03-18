package ru.papino.restaurant.extensions

import com.google.gson.Gson
import ru.papino.restaurant.presentation.basket.models.BasketUIModel

internal fun String.toBasket(): BasketUIModel? =
    Gson().fromJson(this, BasketUIModel::class.java)

internal fun String.toPrice(): Double = this.replace(" ", "").toDoubleOrNull() ?: 0.0