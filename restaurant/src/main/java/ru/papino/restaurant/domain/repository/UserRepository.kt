package ru.papino.restaurant.domain.repository

import ru.papino.restaurant.domain.repository.models.UserModel
import ru.papino.restaurant.domain.repository.models.UserResponse

internal interface UserRepository {
    suspend fun create(user: UserModel): UserResponse

    suspend fun getUserByToken(token: String): UserResponse

    suspend fun getUserByPassword(login: String, password: String): UserResponse

    suspend fun update(
        id: Long,
        firstName: String?,
        secondName: String?,
        address: String?
    ): UserResponse
}