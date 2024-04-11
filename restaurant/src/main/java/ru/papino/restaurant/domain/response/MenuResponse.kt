package ru.papino.restaurant.domain.response

import ru.papino.restaurant.core.net.repeater.RequestRepeatError
import ru.papino.restaurant.domain.models.ProductModel

internal sealed class MenuResponse {

    data class Success(val products: List<ProductModel>) : MenuResponse()

    data class Error(val error: Exception) : MenuResponse(),
        RequestRepeatError
}