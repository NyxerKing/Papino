package ru.papino.restaurant.core.user.di

import ru.papino.restaurant.core.user.models.User

internal object UserDI {
    private lateinit var _user: User
    private var _isUserInitializer = false

    val user by lazy { _user }

    fun init(data: User) {
        _user = data
        _isUserInitializer = true
    }

    fun isUserInitializer() = _isUserInitializer
}