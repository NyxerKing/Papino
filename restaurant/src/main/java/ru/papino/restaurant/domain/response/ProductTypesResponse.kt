package ru.papino.restaurant.domain.response

import ru.papino.restaurant.core.net.repeater.RequestRepeatError
import ru.papino.restaurant.domain.models.ProductTypeModel

internal sealed class ProductTypesResponse {

    data class Success(val products: List<ProductTypeModel>) : ProductTypesResponse()

    data class Error(val error: Exception) : ProductTypesResponse(), RequestRepeatError
}