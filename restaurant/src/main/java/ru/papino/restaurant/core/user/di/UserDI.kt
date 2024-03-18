package ru.papino.restaurant.core.user.di

import ru.papino.restaurant.core.user.models.Token
import ru.papino.restaurant.core.user.models.User

internal object UserDI {
    const val BONUS = 0L

    private lateinit var _user: User
    private lateinit var _token: Token
    private var _isUserInitializer = false

    val user by lazy { _user }
    val token by lazy { _token }

    fun init(data: User) {
        _user = data
        _isUserInitializer = true
    }

    fun initToken(data: Token) {
        _token = data
    }

    fun isUserInitializer() = _isUserInitializer
}