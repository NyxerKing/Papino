package ru.papino.restaurant.data.repository

import ru.papino.restaurant.core.net.repeater.RequestRepeat
import ru.papino.restaurant.data.datasource.net.impl.NetDataSource
import ru.papino.restaurant.data.datasource.net.models.ProductTypesJsonModel
import ru.papino.restaurant.data.datasource.net.services.ProductTypeService
import ru.papino.restaurant.data.mappers.ProductTypesMapper
import ru.papino.restaurant.domain.repository.ProductTypesRepository
import ru.papino.restaurant.domain.response.ProductTypesResponse

internal class ProductTypesRepositoryImpl(
    private val netDS: NetDataSource,
    private val mapper: ProductTypesMapper
) : ProductTypesRepository {

    private val requestRepeat = RequestRepeat<ProductTypesResponse>()
    override suspend fun request(): ProductTypesResponse {
        return requestRepeat.execute(
            onRequest = ::execute,
            defaultResponse = ProductTypesResponse.Error(error = Exception(ERROR_MESSAGE))
        )
    }

    private fun execute(): ProductTypesResponse {
        val service = netDS.getRetrofit().create(ProductTypeService::class.java)
        val response: retrofit2.Response<List<ProductTypesJsonModel>>
        try {
            response = service.getProductTypes().execute()
            response.body()?.let { list ->
                return ProductTypesResponse.Success(products = list.map { type ->
                    mapper.toDomain(
                        type
                    )
                })
            }
        } catch (ex: Throwable) {
            return ProductTypesResponse.Error(error = Exception(ex.message))
        }
        return ProductTypesResponse.Error(
            error = Exception(response?.code()?.toString() ?: ERROR_MESSAGE)
        )
    }

    companion object {
        private const val ERROR_MESSAGE = "Неизвестная ошибка"

        fun getInstance(): ProductTypesRepository = ProductTypesRepositoryImpl(
            netDS = NetDataSource.getInstance(),
            mapper = ProductTypesMapper()
        )
    }
}