package ru.papino.restaurant.data.mappers

import ru.papino.restaurant.data.datasource.net.models.ProductTypesJsonModel
import ru.papino.restaurant.domain.repository.models.ProductTypeModel

internal class ProductTypesMapper {

    fun toDomain(data: ProductTypesJsonModel) = ProductTypeModel(
        name = data.name,
        typeValue = data.typeValue
    )
}