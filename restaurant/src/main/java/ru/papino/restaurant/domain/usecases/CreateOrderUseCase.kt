package ru.papino.restaurant.domain.usecases

import ru.papino.restaurant.domain.repository.OrdersRepository
import ru.papino.restaurant.domain.repository.models.OrderRequestModel

internal class CreateOrderUseCase(
    private val ordersRepository: OrdersRepository
) {

    suspend operator fun invoke(data: OrderRequestModel) =
        ordersRepository.createOrder(data)
}