package ru.papino.restaurant.domain.repository

import ru.papino.restaurant.domain.repository.models.TokenResponse
import ru.papino.restaurant.domain.repository.models.UserModel
import ru.papino.restaurant.domain.repository.models.UserResponse

internal interface UserRepository {
    fun create(user: UserModel): TokenResponse

    fun getUserByToken(token: String): UserResponse

    fun getUserByPassword(login: String, password: String): UserResponse
}