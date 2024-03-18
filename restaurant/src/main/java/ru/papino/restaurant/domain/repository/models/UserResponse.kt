package ru.papino.restaurant.domain.repository.models

import ru.papino.restaurant.core.user.models.Token
import ru.papino.restaurant.core.user.models.User

internal sealed class UserResponse {

    data class Success(
        val user: User,
        val token: Token
    ) : UserResponse()

    data class Error(val message: String) : UserResponse()
}