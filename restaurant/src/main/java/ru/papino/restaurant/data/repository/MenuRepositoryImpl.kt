package ru.papino.restaurant.data.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.delay
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
        var countRequests = 0
        while (countRequests < COUNT_ATTEMPTS) {
            countRequests++

            Log.i(TAG, "attempt request = $countRequests")
            when (val response = execute()) {
                is MenuResponse.Success -> return response
                is MenuResponse.Error -> delay(SLEEP_TIME)
            }
        }

        // todo Убрать после настройки сервака
        val list = Gson().fromJson(localDS.getData(), MenuJsonModel::class.java)
        return mapper.toDomainError(list)
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
//End of input at line 1 column 1 path $