package ru.papino.restaurant.presentation.authorization.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.data.mappers.UserMapper
import ru.papino.restaurant.domain.repository.models.UserResponse
import ru.papino.restaurant.domain.usecases.GetUserByPasswordUseCase

internal class AuthorizationViewModel(
    private val getUserByPasswordUseCase: GetUserByPasswordUseCase,
    private val userMapper: UserMapper
) : ViewModel() {

    private val _onSuccess = MutableSharedFlow<UserResponse.Success>()
    val onSuccess = _onSuccess.asSharedFlow()

    private val _onFailure = MutableSharedFlow<UserResponse.Error>()
    val onFailure = _onFailure.asSharedFlow()

    fun loginUser(
        login: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getUserByPasswordUseCase(login, password)
            }.onSuccess { response ->
                when (response) {
                    is UserResponse.Success -> {
                        response.user.error?.let { errorMessage ->
                            _onFailure.emit(UserResponse.Error(message = errorMessage))
                        } ?: run {
                            UserDI.init(userMapper.toUser(response))
                            UserDI.initToken(response.token)
                            _onSuccess.emit(response)
                        }
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