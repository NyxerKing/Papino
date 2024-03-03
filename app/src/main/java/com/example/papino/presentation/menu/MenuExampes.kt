package com.example.papino.presentation.menu

import com.example.papino.presentation.menu.models.FoodModel
import com.example.papino.presentation.menu.models.FoodVarsModel

fun examplesMenu() = listOf(
    FoodModel(
        id = 1,
        name = "Пепперони",
        detailsFood = "Соус из томатов, острая колбаска, моццарелла, оливковое масло",
        foodSize =
        FoodVarsModel(
            size = 20,
            price = 25000

        )

    ),
    FoodModel(
        id = 2,
        name = "Пепперони",
        detailsFood = "Соус из томатов, острая колбаска, моццарелла, оливковое масло",
        foodSize =
        FoodVarsModel(
            size = 40,
            price = 35000
        )
    )    ,
    FoodModel(
        id = 2,
        name = "Пицца Маргарита",
        detailsFood = "Томатный соус, моццарелла, зеленый базилик",
        foodSize =
        FoodVarsModel(
            size = 25,
            price = 30000
        )
    )

    ,
    FoodModel(
        id = 2,
        name = "Пицца Маргарита",
        detailsFood = "Соус из томатов, острая колбаска, моццарелла, оливковое масло",
        foodSize =
        FoodVarsModel(
            size = 40,
            price = 55000
        )
    )
)


