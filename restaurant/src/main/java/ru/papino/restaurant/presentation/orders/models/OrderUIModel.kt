package ru.papino.restaurant.presentation.orders.models

import ru.papino.restaurant.domain.status.OrderStatus

internal data class OrderUIModel(
    val id: Long,
    val created: String,
    val products: List<String>,
    val sum: Double,
    val status: OrderStatus
)
