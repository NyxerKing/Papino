package ru.papino.restaurant.domain.repository

import ru.papino.restaurant.domain.response.AboutResponse

internal interface AboutRepository {

    suspend fun getAbout(): AboutResponse
}