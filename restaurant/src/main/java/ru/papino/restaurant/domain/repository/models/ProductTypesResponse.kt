package ru.papino.restaurant.domain.repository.models

import ru.papino.restaurant.core.net.repeater.RequestRepeatError

internal sealed class ProductTypesResponse {

    data class Success(val products: List<ProductTypeModel>) : ProductTypesResponse()

    data class Error(val error: Exception) : ProductTypesResponse(), RequestRepeatError
}