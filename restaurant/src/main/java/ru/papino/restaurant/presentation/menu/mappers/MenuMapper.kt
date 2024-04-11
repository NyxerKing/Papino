package ru.papino.restaurant.presentation.menu.mappers

import ru.papino.restaurant.core.net.BASE_URL
import ru.papino.restaurant.domain.models.ProductModel
import ru.papino.restaurant.domain.response.MenuResponse
import ru.papino.restaurant.domain.response.ProductTypesResponse
import ru.papino.restaurant.presentation.menu.models.ProductTypeUIModel
import ru.papino.restaurant.presentation.menu.models.ProductUIModel

internal class MenuMapper {

    fun toUI(data: MenuResponse): List<ProductUIModel>? {
        return when (data) {
            is MenuResponse.Success -> {
                data.products.map { domain ->
                    ProductUIModel(
                        id = domain.id,
                        name = domain.name,
                        size = domain.size,
                        price = domain.price,
                        details = domain.details,
                        typeProduct = domain.typeProduct,
                        linkCover = BASE_URL + domain.linkCover
                    )
                }
            }

            is MenuResponse.Error -> {
                null
            }
        }
    }

    fun toProductTypesUI(data: ProductTypesResponse): List<ProductTypeUIModel>? {
        return when (data) {
            is ProductTypesResponse.Success -> {
                data.products.map { domain ->
                    ProductTypeUIModel(
                        title = domain.name,
                        typeProduct = domain.typeValue
                    )
                }
            }

            is ProductTypesResponse.Error -> {
                null
            }
        }
    }

    fun toProductDomain(data: ProductUIModel) = ProductModel(
        id = data.id,
        name = data.name,
        size = data.size,
        price = data.price,
        details = data.details,
        typeProduct = data.typeProduct,
        linkCover = BASE_URL + data.linkCover
    )
}