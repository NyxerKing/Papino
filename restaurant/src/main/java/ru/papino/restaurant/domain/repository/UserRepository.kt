package ru.papino.restaurant.domain.repository

import ru.papino.restaurant.core.user.models.User

internal interface UserRepository {
    fun create(user: User)

    fun getUserByToken(token: String): User

    fun getUserByPassword(login: String, password: String): User
}