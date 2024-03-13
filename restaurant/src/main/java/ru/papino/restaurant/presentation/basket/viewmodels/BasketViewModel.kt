package ru.papino.restaurant.presentation.basket.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.papino.restaurant.domain.repository.BasketRepository
import ru.papino.restaurant.presentation.basket.models.BasketUIModel

internal class BasketViewModel(
    private val basketRepository: BasketRepository
) : ViewModel() {

    private val _basket = MutableSharedFlow<List<BasketUIModel>>()
    val basket = _basket.asSharedFlow()

    init {
        loadBasket()
    }

    private fun loadBasket() {
        viewModelScope.launch {
            val list = mutableListOf<BasketUIModel>()
            val basketProducts = basketRepository.getAll()

            basketProducts.forEach { model ->
                model.id?.let { id ->
                    val count = basketRepository.getCount(id)
                    list.add(
                        BasketUIModel(
                            id = id,
                            name = model.name.orEmpty(),
                            size = model.size.orEmpty(),
                            price = model.price.orEmpty(),
                            count = count
                        )
                    )
                }
            }

            _basket.emit(list)
        }
    }
}