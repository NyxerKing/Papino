package ru.papino.restaurant.domain.repository

import ru.papino.restaurant.domain.repository.models.AboutResponse

internal interface AboutRepository {

    suspend fun getAbout(): AboutResponse
}