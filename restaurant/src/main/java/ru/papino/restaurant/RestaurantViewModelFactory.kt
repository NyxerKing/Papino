package ru.papino.restaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.papino.restaurant.domain.usecases.GetAboutUseCase
import ru.papino.restaurant.domain.usecases.GetUserByTokenUseCase

internal class RestaurantViewModelFactory(
    private val getAboutUseCase: GetAboutUseCase,
    private val getUserByTokenUseCase: GetUserByTokenUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestaurantViewModel(
            getAboutUseCase = getAboutUseCase,
            getUserByTokenUseCase = getUserByTokenUseCase
        ) as T
    }
}