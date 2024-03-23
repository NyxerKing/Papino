package ru.papino.restaurant.data.repository

import ru.papino.restaurant.data.datasource.net.impl.NetDataSource
import ru.papino.restaurant.data.datasource.net.services.OrdersService
import ru.papino.restaurant.data.mappers.OrdersMapper
import ru.papino.restaurant.domain.repository.OrdersRepository
import ru.papino.restaurant.domain.repository.models.OrdersResponse

internal class OrdersRepositoryImpl(
    private val netDS: NetDataSource,
    private val mapper: OrdersMapper
) : OrdersRepository {
    override suspend fun getOrders(userId: Long): OrdersResponse {
        val service = netDS.getRetrofit().create(OrdersService::class.java)
        try {
            service.getOrders().execute().body()
                ?.map {
                    mapper.tuDomain(it)
                }?.let {
                    return OrdersResponse.Success(orders = it)
                }
        } catch (ex: Exception) {
            return OrdersResponse.Error(ex)
        }
        return OrdersResponse.Error(Exception("Данные не получены"))
    }
}