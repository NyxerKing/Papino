package ru.papino.restaurant.presentation.authorization.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.data.mappers.UserMapper
import ru.papino.restaurant.domain.repository.models.UserResponse
import ru.papino.restaurant.domain.usecases.GetUserByPasswordUseCase

internal class AuthorizationViewModel(
    private val getUserByPasswordUseCase: GetUserByPasswordUseCase,
    private val userMapper: UserMapper
) : ViewModel() {

    fun loginUser(
        login: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getUserByPasswordUseCase(login, password)
            }.onSuccess { response ->
                when (response) {
                    is UserResponse.Success -> {
                        UserDI.init(userMapper.toUser(response))
                        UserDI.initToken(response.token)
                        onSuccess()
                    }

                    is UserResponse.Error -> {
                        onFailure(response.message)
                    }
                }
            }.onFailure {
                onFailure(it.toString())
            }
        }
    }
}