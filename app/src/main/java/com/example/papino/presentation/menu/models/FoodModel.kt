package com.example.papino.presentation.menu.models

data class FoodModel(
        val id: Int,
        //val coverLink: String,
        val name: String,

        val detailsFood : String,
        // val foodVars: List<FoodVarsModel>
        val foodSize: FoodVarsModel
)
