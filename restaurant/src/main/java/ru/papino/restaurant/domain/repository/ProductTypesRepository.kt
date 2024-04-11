package ru.papino.restaurant.domain.repository

import ru.papino.restaurant.domain.response.ProductTypesResponse

internal interface ProductTypesRepository {
    suspend fun request(): ProductTypesResponse
}