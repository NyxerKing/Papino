package com.example.papino.net

data class Food (
    val id: Int? = null,
    val namefood: String? = null,
    val sizeportion: String? = null,
    val pricefood : String? = null,
    val detailsfood : String? = null,
    val typeFood : String? = null
)


data class ListFood (
    val group: ArrayList<Food>
)

