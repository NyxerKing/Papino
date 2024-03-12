package com.example.papino.presentation.basket.mappers

import android.content.res.Resources
import com.example.papino.presentation.basket.model.FoodBasketModel
import com.example.papino.presentation.basket.model.FoodBasketUI
import ru.papino.uikit.components.cards.CardBaskedComponent
import ru.papino.uikit.components.controllers.ElementControllerComponent
import ru.papino.uikit.extensions.clearSpace

class BasketMapper(private val resources: Resources) {

    fun toComponentData(
        model: FoodBasketUI,
        deleteOnClick: (() -> Unit)? = null,
        minusOnClick: (() -> Unit)? = null,
        plusOnClick: (() -> Unit)? = null
    ) = CardBaskedComponent.Data(
        title = model.name,
        price = resources.getString(
            ru.papino.uikit.R.string.insert_sum_count,
            model.price.toString()
        ),
        priceAll = resources.getString(
            ru.papino.uikit.R.string.insert_count_sum_all,
            model.count.toString(),
            (model.price * model.count).toString()
        ),
        controllerItemData = toControllerData(model, deleteOnClick, minusOnClick, plusOnClick)
    )

    fun toListUI(models: List<FoodBasketModel>) =
        models.groupBy { model -> model.id }.map { group ->
            FoodBasketUI(
                id = group.key,
                name = "${group.value.first().name} ${group.value.first().size}",
                price = group.value.first().price.clearSpace().toDouble(),
                count = group.value.size,
            )
        }

    private fun toControllerData(
        model: FoodBasketUI,
        deleteOnClick: (() -> Unit)? = null,
        minusOnClick: (() -> Unit)? = null,
        plusOnClick: (() -> Unit)? = null
    ) = ElementControllerComponent.Data(
        count = model.count,
        deleteOnClick = deleteOnClick,
        minusOnClick = minusOnClick,
        plusOnClick = plusOnClick
    )
}