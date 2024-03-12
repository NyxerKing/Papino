package com.example.papino.presentation.basket.model

/**
 * Элемент корзины для отображения в UI
 *
 * @property id уникальный идентификатор элемента
 * @property name наименование продукта (+ размер порции)
 * @property price стоимость 1 еденицы
 * @property count кол-во единиц в корзине
 */
data class FoodBasketUI(
    val id: Int,
    val name: String,
    val price: Double,
    val count: Int
)
