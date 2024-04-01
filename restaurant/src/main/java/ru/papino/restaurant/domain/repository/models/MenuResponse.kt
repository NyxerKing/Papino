package ru.papino.restaurant.domain.repository.models

import ru.papino.restaurant.core.net.repeater.RequestRepeatError

internal sealed class MenuResponse {

    data class Success(val products: List<ProductModel>) : MenuResponse()

    data class Error(val error: Exception, val buffer: List<ProductModel>? = null) : MenuResponse(),
        RequestRepeatError
}