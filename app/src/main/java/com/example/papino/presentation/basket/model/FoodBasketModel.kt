package com.example.papino.presentation.basket.model

import com.example.papino.presentation.menu.models.FoodVarsModel

data class FoodBasketModel(
    var id: Int,
    //val coverLink: String,
    val name: String,
    val detailsFood: String,
    val size: String,
    val price: String

   /* val foodVars: FoodVarsModel*/
)
