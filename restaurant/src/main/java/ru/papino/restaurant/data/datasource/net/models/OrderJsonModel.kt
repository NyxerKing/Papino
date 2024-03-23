package ru.papino.restaurant.data.datasource.net.models

import ru.papino.restaurant.domain.repository.models.status.OrderStatus

// TODO параметры неизвестны
internal data class OrderJsonModel(
    val id: Long,
    val created: String,
    val products: List<String>,
    val sum: Double,
    val status: OrderStatus
)
