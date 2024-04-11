package ru.papino.restaurant.presentation.registration.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.data.mappers.UserMapper
import ru.papino.restaurant.domain.models.UserModel
import ru.papino.restaurant.domain.response.UserResponse
import ru.papino.restaurant.domain.usecases.CreateUserUseCase

internal class RegistrationViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val userMapper: UserMapper
) : ViewModel() {

    private val _onSuccess = MutableSharedFlow<UserResponse.Success>()
    val onSuccess = _onSuccess.asSharedFlow()

    private val _onFailure = MutableSharedFlow<UserResponse.Error>()
    val onFailure = _onFailure.asSharedFlow()

    fun createUser(user: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                createUserUseCase(user = user)
            }.onSuccess { response ->
                when (response) {
                    is UserResponse.Success -> {
                        UserDI.init(userMapper.toUser(response))
                        UserDI.initToken(response.token)
                        _onSuccess.emit(response)
                    }

                    is UserResponse.Error -> {
                        _onFailure.emit(response)
                    }
                }
            }.onFailure {
                _onFailure.emit(UserResponse.Error(message = it.toString()))
            }
        }
    }
}