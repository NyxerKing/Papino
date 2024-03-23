package ru.papino.restaurant.data.repository

import com.google.gson.Gson
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

    override suspend fun request(): MenuResponse {
        val menuService = netDS.getRetrofit().create(MenuService::class.java)
        try {
            menuService.getMenu().execute().body()?.let {
                return mapper.toDomain(it)
            }
        } catch (ex: Throwable) {
            // todo Убрать после настройки сервака
            val list = Gson().fromJson(localDS.getData(), MenuJsonModel::class.java)
            return mapper.toDomainError(list)
        }
        val list = Gson().fromJson(localDS.getData(), MenuJsonModel::class.java)
        return mapper.toDomainError(list)
    }
}