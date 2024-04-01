package ru.papino.restaurant.data.repository

import com.google.gson.Gson
import ru.papino.restaurant.core.net.repeater.RequestRepeat
import ru.papino.restaurant.data.datasource.local.impl.LocalDataSource
import ru.papino.restaurant.data.datasource.net.impl.NetDataSource
import ru.papino.restaurant.data.datasource.net.models.MenuJsonModel
import ru.papino.restaurant.data.datasource.net.services.MenuService
import ru.papino.restaurant.data.mappers.MenuMapper
import ru.papino.restaurant.domain.repository.MenuRepository
import ru.papino.restaurant.domain.repository.models.MenuResponse

internal class MenuRepositoryImpl(
    private val localDS: LocalDataSource,
    private val netDS: NetDataSource,
    private val mapper: MenuMapper
) : MenuRepository {

    private val requestRepeat = RequestRepeat<MenuResponse>()

    override suspend fun request(): MenuResponse {
        val list = Gson().fromJson(localDS.getData(), MenuJsonModel::class.java)

        return requestRepeat.execute(
            onRequest = ::execute,
            defaultResponse = mapper.toDomainError(list)
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

    companion object {
        private const val TAG = "MenuRepository"
        private const val SLEEP_TIME = 500L
        private const val COUNT_ATTEMPTS = 3
    }
}