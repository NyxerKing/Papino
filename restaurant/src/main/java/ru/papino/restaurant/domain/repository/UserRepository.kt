package ru.papino.restaurant.domain.repository

import ru.papino.restaurant.domain.repository.models.TokenResponse
import ru.papino.restaurant.domain.repository.models.UserModel
import ru.papino.restaurant.domain.repository.models.UserResponse

internal interface UserRepository {
    suspend fun create(user: UserModel): TokenResponse

    suspend fun getUserByToken(token: String): UserResponse

    suspend fun getUserByPassword(login: String, password: String): UserResponse
}