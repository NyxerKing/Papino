package ru.papino.restaurant.domain.models

internal data class OrderRequestModel(
    val userId: Long,
    val useBonus: Boolean,
    val address: String,
    val sum: Double,
    val products: List<OrderProductRequestModel>
)

internal data class OrderProductRequestModel(
    val productId: Long,
    val count: Int
)