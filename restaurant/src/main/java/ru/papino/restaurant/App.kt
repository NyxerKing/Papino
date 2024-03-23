package ru.papino.restaurant

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.core.user.encrypted.EncryptedToken
import ru.papino.restaurant.data.repository.UserRepositoryImpl
import ru.papino.restaurant.domain.repository.models.UserResponse
import ru.papino.restaurant.domain.usecases.GetUserByTokenUseCase

class App : Application() {
    private val getUserByTokenUseCase =
        GetUserByTokenUseCase(userRepository = UserRepositoryImpl.getInstance())

    override fun onCreate() {
        super.onCreate()
        authorizationByToken()
    }

    private fun authorizationByToken() {
        val token = EncryptedToken.getToken()
        token?.let {
            CoroutineScope(Dispatchers.IO).launch {
                runCatching {
                    getUserByTokenUseCase(it)
                }.onSuccess { response ->
                    when (response) {
                        is UserResponse.Success -> {
                            UserDI.init(response.user)
                            UserDI.initToken(response.token)
                        }

                        is UserResponse.Error -> {
                            UserDI.clear()
                        }
                    }
                }.onFailure {
                    UserDI.clear()
                }
            }
        }
    }
}