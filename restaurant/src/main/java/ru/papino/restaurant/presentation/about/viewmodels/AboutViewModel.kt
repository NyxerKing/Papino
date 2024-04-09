package ru.papino.restaurant.presentation.about.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.papino.restaurant.domain.repository.models.AboutModel
import ru.papino.restaurant.domain.repository.models.AboutResponse
import ru.papino.restaurant.domain.usecases.GetAboutUseCase

internal class AboutViewModel(
    private val getAboutUseCase: GetAboutUseCase
) : ViewModel() {

    private val _about = MutableStateFlow<AboutModel?>(null)
    val about = _about.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getAboutUseCase()
            }.onSuccess { response ->
                when (response) {
                    is AboutResponse.Success -> {
                        _about.emit(response.data)
                    }

                    is AboutResponse.Error -> {
                        _error.emit(response.error.message.toString())
                    }
                }
            }.onFailure {
                _error.emit(it.message.toString())
            }
        }
    }
}