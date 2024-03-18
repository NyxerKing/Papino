package ru.papino.restaurant.domain.repository.models

internal sealed class MenuResponse {

    data class Success(val products: List<ProductModel>) : MenuResponse()

    data class Error(val error: Exception, val buffer: List<ProductModel>) : MenuResponse()
}