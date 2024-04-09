package ru.papino.restaurant.data.repository

import ru.papino.restaurant.data.datasource.net.impl.NetDataSource
import ru.papino.restaurant.data.datasource.net.services.AboutService
import ru.papino.restaurant.data.mappers.AboutMapper
import ru.papino.restaurant.domain.repository.AboutRepository
import ru.papino.restaurant.domain.repository.models.AboutResponse

internal class AboutRepositoryImpl(
    private val netDS: NetDataSource,
    private val aboutMapper: AboutMapper
) : AboutRepository {
    override suspend fun getAbout(): AboutResponse {
        val service = netDS.getRetrofit().create(AboutService::class.java)
        try {
            val response = service.getAbout().execute()
            response.body()?.let {
                return AboutResponse.Success(data = aboutMapper.toDomain(it))
            } ?: return AboutResponse.Error(Exception("Данные не получены"))
        } catch (ex: Exception) {
            return AboutResponse.Error(ex)
        }
    }
}