package ru.papino.restaurant.core.user.di

import ru.papino.restaurant.core.user.models.User

object UserDI {
    private lateinit var _user: User

    val user by lazy { _user }

    fun init(data: User) {
        _user = data
    }
}