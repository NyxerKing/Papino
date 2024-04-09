package ru.papino.restaurant.presentation.basket.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.papino.restaurant.domain.repository.BasketRepository
import ru.papino.restaurant.domain.repository.models.OrderRequestModel
import ru.papino.restaurant.domain.usecases.CreateOrderUseCase
import ru.papino.restaurant.presentation.basket.mappers.BasketMapper
import ru.papino.restaurant.presentation.basket.models.BasketUIModel

internal class BasketViewModel(
    private val basketRepository: BasketRepository,
    private val createOrderUseCase: CreateOrderUseCase,
    private val basketMapper: BasketMapper
) : ViewModel() {

    private val _basket = MutableSharedFlow<List<BasketUIModel>>()
    val basket = _basket.asSharedFlow()

    private var bufferBasket: List<BasketUIModel> = listOf()

    fun createOrder(
        userId: Long,
        useBonus: Boolean,
        address: String,
        sum: Double,
        onComplete: (error: Throwable?) -> Unit
    ) {
        val order = OrderRequestModel(
            userId = userId,
            useBonus = useBonus,
            address = address,
            sum = sum,
            products = bufferBasket.map { product -> basketMapper.toDomain(product) }
        )
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                createOrderUseCase(order)
            }.onSuccess {
                onComplete(null)
            }.onFailure {
                onComplete(it)
            }
        }
    }

    fun deleteProduct(model: BasketUIModel) {
        viewModelScope.launch {
            basketRepository.delete(model.id)
            loadBasket()
        }
    }

    fun minusProduct(model: BasketUIModel) {
        viewModelScope.launch {
            if (model.count == 1) {
                basketRepository.delete(model.id)
            } else {
                basketRepository.update(model.id, model.count - 1)
            }
            loadBasket()
        }
    }

    fun plusProduct(model: BasketUIModel) {
        viewModelScope.launch {
            basketRepository.update(model.id, model.count + 1)
            loadBasket()
        }
    }

    fun loadBasket() {
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

            bufferBasket = list
            _basket.emit(list)
        }
    }

    fun checkOrderParameters(userId: Long, address: String, sum: Double, useBonusSum: Long) =
        bufferBasket.isNotEmpty() && userId > 0 && address.isNotEmpty() && (sum > 0 || useBonusSum > 0)

}