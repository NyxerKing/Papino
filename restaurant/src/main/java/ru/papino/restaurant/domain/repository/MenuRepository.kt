package ru.papino.restaurant.domain.repository

import ru.papino.restaurant.domain.repository.models.MenuResponse

internal interface MenuRepository {
    suspend fun request(): MenuResponse
}