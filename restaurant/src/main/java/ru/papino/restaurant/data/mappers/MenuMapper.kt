package ru.papino.restaurant.data.mappers

import ru.papino.restaurant.data.datasource.net.models.MenuJsonModel
import ru.papino.restaurant.domain.repository.models.MenuResponse
import ru.papino.restaurant.domain.repository.models.ProductModel

internal class MenuMapper {

    fun toDomain(data: MenuJsonModel) = MenuResponse.Success(
        data.products.map { dto ->
            ProductModel(
                id = dto.id,
                name = dto.name,
                size = dto.size,
                price = dto.price,
                details = dto.details,
                typeProduct = dto.typeProduct,
                linkCover = dto.linkCover
            )
        }
    )

    fun toDomainError(data: MenuJsonModel?, th: Throwable? = null) = MenuResponse.Error(
        Exception("Данные взяты из локального хранилища\n${th?.message}"),
        data?.products?.map { dto ->
            ProductModel(
                id = dto.id,
                name = dto.name,
                size = dto.size,
                price = dto.price,
                details = dto.details,
                typeProduct = dto.typeProduct,
                linkCover = dto.linkCover
            )
        }
    )
}