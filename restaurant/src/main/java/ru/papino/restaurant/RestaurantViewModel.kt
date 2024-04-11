package ru.papino.restaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.core.user.encrypted.EncryptedToken
import ru.papino.restaurant.domain.response.AboutResponse
import ru.papino.restaurant.domain.response.UserResponse
import ru.papino.restaurant.domain.usecases.GetAboutUseCase
import ru.papino.restaurant.domain.usecases.GetUserByTokenUseCase

internal class RestaurantViewModel(
    private val getAboutUseCase: GetAboutUseCase,
    private val getUserByTokenUseCase: GetUserByTokenUseCase
) : ViewModel() {

    private val _about = MutableSharedFlow<AboutResponse>()
    val about = _about.asSharedFlow()

    fun loadAbout() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getAboutUseCase()
            }.onSuccess { response ->
                _about.emit(response)
            }.onFailure {
                _about.emit(AboutResponse.Error(Exception(it.message)))
            }
        }
    }

    fun authorizationByToken() {
        val token = EncryptedToken.getToken()
        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
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