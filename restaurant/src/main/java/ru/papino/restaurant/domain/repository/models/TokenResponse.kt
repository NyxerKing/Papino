package ru.papino.restaurant.domain.repository.models

import ru.papino.restaurant.core.user.models.Token

internal sealed class TokenResponse {

    data class Success(val token: Token, val userId: Long) : TokenResponse()

    data class Error(val message: String) : TokenResponse()
}