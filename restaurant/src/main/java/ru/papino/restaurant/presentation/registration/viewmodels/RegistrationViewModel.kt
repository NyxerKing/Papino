package ru.papino.restaurant.presentation.registration.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.core.user.models.User
import ru.papino.restaurant.domain.repository.models.TokenResponse
import ru.papino.restaurant.domain.repository.models.UserModel
import ru.papino.restaurant.domain.usecases.CreateUserUseCase

internal class RegistrationViewModel(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    fun createUser(user: UserModel, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                createUserUseCase(user = user)
            }.onSuccess { response ->
                when (response) {
                    is TokenResponse.Success -> {
                        UserDI.init(
                            User(
                                firstName = user.firstName,
                                secondName = user.secondName,
                                phone = user.phone,
                                address = user.address
                            )
                        )
                        UserDI.initToken(response.token)
                        onSuccess()
                    }

                    is TokenResponse.Error -> {
                        onFailure(response.message)
                    }
                }
            }.onFailure {
                onFailure(it.toString())
            }
        }
    }
}