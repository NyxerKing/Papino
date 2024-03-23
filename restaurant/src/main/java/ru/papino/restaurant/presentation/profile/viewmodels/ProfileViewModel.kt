package ru.papino.restaurant.presentation.profile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.core.user.models.User

internal class ProfileViewModel : ViewModel() {

    private val _user = MutableSharedFlow<User>()
    val user = _user.asSharedFlow()

    fun loadUser() {
        viewModelScope.launch {
            if (UserDI.isUserInitializer()) _user.emit(UserDI.user)
        }
    }
}