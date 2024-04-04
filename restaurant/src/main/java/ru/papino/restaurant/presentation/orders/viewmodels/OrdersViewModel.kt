package ru.papino.restaurant.presentation.orders.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.domain.repository.models.OrdersResponse
import ru.papino.restaurant.domain.usecases.GetOrdersUseCase
import ru.papino.restaurant.presentation.orders.mappers.OrdersMapper
import ru.papino.restaurant.presentation.orders.models.OrderUIModel

internal class OrdersViewModel(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val mapper: OrdersMapper
) : ViewModel() {

    private val _orders = MutableSharedFlow<List<OrderUIModel>>()
    val orders = _orders.asSharedFlow()

    private val _error = MutableSharedFlow<Throwable>()
    val error = _error.asSharedFlow()

    private val _loader = MutableStateFlow(false)
    val loader = _loader.asStateFlow()

    fun loadOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            _loader.emit(true)
            runCatching {
                if (UserDI.isUserInitializer()) {
                    getOrdersUseCase(UserDI.user.id)
                } else {
                    OrdersResponse.Error(Exception("Пользователь не авторизован"))
                }

            }.onSuccess { response ->
                when (response) {
                    is OrdersResponse.Success -> {
                        val list = response.orders.map { orderModel -> mapper.toUI(orderModel) }
                        _orders.emit(list)
                    }

                    is OrdersResponse.Error -> {
                        _error.emit(Throwable(response.error))
                    }
                }
                _loader.emit(false)
            }.onFailure {
                _error.emit(it)
                _loader.emit(false)
            }
        }
    }
}