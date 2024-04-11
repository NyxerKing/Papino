package ru.papino.restaurant.data.repository

import ru.papino.restaurant.core.net.repeater.RequestRepeat
import ru.papino.restaurant.data.datasource.net.impl.NetDataSource
import ru.papino.restaurant.data.datasource.net.models.MenuJsonModel
import ru.papino.restaurant.data.datasource.net.services.MenuService
import ru.papino.restaurant.data.mappers.MenuMapper
import ru.papino.restaurant.domain.repository.MenuRepository
import ru.papino.restaurant.domain.response.MenuResponse

internal class MenuRepositoryImpl(
    private val netDS: NetDataSource,
    private val mapper: MenuMapper
) : MenuRepository {

    private val requestRepeat = RequestRepeat<MenuResponse>()

    override suspend fun request(): MenuResponse {
        return requestRepeat.execute(
            onRequest = ::execute,
            defaultResponse = mapper.toDomainError(Exception("Данные не получены"))
        )
    }

    private fun execute(): MenuResponse {
        val menuService = netDS.getRetrofit().create(MenuService::class.java)
        val response: retrofit2.Response<MenuJsonModel>?
        try {
            response = menuService.getMenu().execute()
            response?.body()?.let {
                return mapper.toDomain(it)
            }
        } catch (ex: Throwable) {
            return MenuResponse.Error(error = Exception(ex.message))
        }
        return MenuResponse.Error(
            error = Exception(response?.code()?.toString() ?: "Неизвестная ошибка")
        )
    }
}