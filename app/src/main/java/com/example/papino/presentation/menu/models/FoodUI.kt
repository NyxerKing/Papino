package com.example.papino.presentation.menu.models

/**
 * Модель данных для presentations слоя
 *
 * @property id уникальный идентификатор
 * @property name наименование продукта
 * @property sizePortion размер порции
 * @property price цена
 * @property details описание
 * @property type тип
 * @property linkCover ссылка на картинку продукта
 * @property isInBasket находится ли в корзине
 */
data class FoodUI(
    val id: Int,
    val name: String,
    val sizePortion: String,
    val price: String,
    val details: String,
    val type: String,
    val linkCover: String,
    var isInBasket: Boolean = false
)
