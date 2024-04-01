package ru.papino.restaurant.domain.repository.models.status

internal enum class OrderStatus {
    CREATED,
    WORKING,
    DELIVERED,
    COMPLETED,
    CANCELED
}