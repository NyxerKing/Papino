package ru.papino.restaurant.presentation.menu.models

internal data class ProductUIModel(
    val id: Int? = null,
    val name: String? = null,
    val size: String? = null,
    val price: String? = null,
    val details: String? = null,
    val typeProduct: String? = null,
    val linkCover: String? = null,
    var isBasket: Boolean = false
)