package ru.papino.restaurant.data.datasource.room.mappers

import ru.papino.restaurant.data.datasource.room.models.BasketProductEntity
import ru.papino.restaurant.domain.models.ProductModel

internal class RoomBasketMapper {

    fun toDomain(data: List<BasketProductEntity>): List<ProductModel> = data.map { entity ->
        ProductModel(
            id = entity.id,
            name = entity.name,
            size = entity.size,
            price = entity.price,
            details = entity.details,
            typeProduct = entity.typeProduct,
            linkCover = entity.linkCover,
        )
    }

    fun toEntity(data: ProductModel) =
        BasketProductEntity(
            id = data.id ?: 0,
            name = data.name.orEmpty(),
            size = data.size.orEmpty(),
            price = data.price.orEmpty(),
            details = data.details.orEmpty(),
            typeProduct = data.typeProduct.orEmpty(),
            linkCover = data.linkCover.orEmpty(),
            count = 1
        )
}