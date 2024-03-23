package ru.papino.restaurant.domain.repository.models

import ru.papino.restaurant.domain.repository.models.status.OrderStatus

internal data class OrderModel(
    val id: Long,
    val created: String,
    val products: List<String>,
    val sum: Double,
    val status: OrderStatus
)
