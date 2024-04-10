package ru.papino.restaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.papino.restaurant.domain.usecases.GetAboutUseCase

internal class RestaurantViewModelFactory(
    private val getAboutUseCase: GetAboutUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestaurantViewModel(getAboutUseCase) as T
    }
}