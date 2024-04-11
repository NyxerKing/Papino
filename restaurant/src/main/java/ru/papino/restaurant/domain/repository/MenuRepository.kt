package ru.papino.restaurant.domain.repository

import ru.papino.restaurant.domain.response.MenuResponse

internal interface MenuRepository {
    suspend fun request(): MenuResponse
}