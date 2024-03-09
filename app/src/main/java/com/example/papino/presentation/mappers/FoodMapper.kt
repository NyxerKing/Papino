package com.example.papino.presentation.mappers

import com.example.papino.net.ListFood
import com.example.papino.presentation.basket.model.FoodBasketModel
import com.example.papino.presentation.menu.models.FoodUI
import com.example.papino.presentation.menu.models.PackFoodBaskedModel

internal class FoodMapper {

    /**
     * Конвертировать в UI
     *
     * @param foods
     * @return
     */
    fun toUI(foods: ListFood): List<FoodUI> = foods.group.map { food ->
        FoodUI(
            id = food.id ?: 0,
            name = food.namefood.orEmpty(),
            sizePortion = food.sizeportion.orEmpty(),
            price = food.pricefood.orEmpty(),
            details = food.detailsfood.orEmpty(),
            type = food.typeFoodid.orEmpty(),
            linkCover = food.uriImageFood.orEmpty()
        )
    }

    /**
     * Конвертировать в модель UI для корзины
     *
     * @param foodUI
     * @return
     */
    fun toBasketUI(foodUI: FoodUI): FoodBasketModel = FoodBasketModel(
        id = foodUI.id,
        name = foodUI.name,
        detailsFood = foodUI.details,
        size = foodUI.sizePortion,
        price = foodUI.price,
        uriImageFood = foodUI.linkCover
    )

    fun toUI(pack: PackFoodBaskedModel): List<FoodUI> = pack.dataList.map { item ->
        FoodUI(
            id = item.id,
            name = item.name,
            sizePortion = item.size,
            price = item.price,
            details = item.detailsFood,
            type = "",
            linkCover = item.uriImageFood
        )
    }
}