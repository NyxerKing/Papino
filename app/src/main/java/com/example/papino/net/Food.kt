package com.example.papino.net

data class Food (
    val id: Int? = null,
    val namefood: String? = null,
    val sizeportion: String? = null,
    val pricefood : String? = null,
    val detailsfood : String? = null,
    val typeFoodid : String? = null,
    val uriImageFood: String? = null
)


data class ListFood (
    val group: List<Food>
)

