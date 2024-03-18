package ru.papino.restaurant.domain.repository.models

internal sealed class ProductTypesResponse {

    data class Success(val products: List<ProductTypeModel>) : ProductTypesResponse()

    data class Error(val error: Exception) : ProductTypesResponse()
}