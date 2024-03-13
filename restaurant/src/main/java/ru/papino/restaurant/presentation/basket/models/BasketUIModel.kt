package ru.papino.restaurant.presentation.basket.models

internal data class BasketUIModel(
    val id: Int,
    val name: String,
    val size: String,
    val price: String,
    val count: Int
)