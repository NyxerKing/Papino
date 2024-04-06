package ru.papino.restaurant.core.user.di

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.papino.restaurant.core.user.encrypted.EncryptedToken
import ru.papino.restaurant.core.user.models.Token
import ru.papino.restaurant.core.user.models.User

internal object UserDI {
    const val BONUS_DEFAULT = 0L

    private lateinit var _user: User
    private lateinit var _token: Token
    private var _isUserInitializer = false
    private val _initUser = MutableStateFlow<User?>(null)

    val user get() = _user
    val onInitUser = _initUser.asStateFlow()

    suspend fun init(data: User) {
        _user = data
        _isUserInitializer = true
        _initUser.emit(_user)
    }

    fun initToken(data: Token) {
        _token = data
        EncryptedToken.save(_token)
    }

    fun clear() {
        // todo нужно реализовать очистку или заглушку
    }

    fun isUserInitializer() = _isUserInitializer
}