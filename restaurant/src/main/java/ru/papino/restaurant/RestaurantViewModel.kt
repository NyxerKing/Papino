package ru.papino.restaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.papino.restaurant.domain.repository.models.AboutResponse
import ru.papino.restaurant.domain.usecases.GetAboutUseCase

internal class RestaurantViewModel(
    private val getAboutUseCase: GetAboutUseCase
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
}