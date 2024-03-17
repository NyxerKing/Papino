package ru.papino.restaurant.presentation.registration.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.domain.repository.models.UserModel
import ru.papino.restaurant.domain.repository.models.UserResponse
import ru.papino.restaurant.domain.usecases.CreateUserUseCase

internal class RegistrationViewModel(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    fun createUser(user: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                createUserUseCase(user = user)
            }.onSuccess { response ->
                when (response) {
                    is UserResponse.Success -> {
                        UserDI.init(response.user)
                    }

                    is UserResponse.Error -> {}
                }
            }.onFailure {

            }
        }
    }
}