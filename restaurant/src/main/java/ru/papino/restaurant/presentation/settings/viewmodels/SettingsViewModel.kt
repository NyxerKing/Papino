package ru.papino.restaurant.presentation.settings.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.core.user.models.User
import ru.papino.restaurant.data.mappers.UserMapper
import ru.papino.restaurant.domain.repository.models.UserResponse
import ru.papino.restaurant.domain.usecases.UpdateUserUseCase

internal class SettingsViewModel(
    private val updateUserUseCase: UpdateUserUseCase,
    private val userMapper: UserMapper
) : ViewModel() {

    private val _user = MutableSharedFlow<User>()
    val user = _user.asSharedFlow()

    fun loadUser() {
        viewModelScope.launch {
            if (UserDI.isUserInitializer()) _user.emit(UserDI.user)
        }
    }

    fun updateUser(
        secondName: String?,
        firstName: String?,
        address: String?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                updateUserUseCase(secondName, firstName, address)
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