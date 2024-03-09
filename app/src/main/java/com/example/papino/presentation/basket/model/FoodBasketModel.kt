package com.example.papino.presentation.basket.model

data class FoodBasketModel(
    var id: Int,
    val name: String,
    val detailsFood: String,
    val size: String,
    val price: String,
    val uriImageFood: String
)
