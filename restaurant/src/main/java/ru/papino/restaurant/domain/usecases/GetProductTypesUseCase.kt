package ru.papino.restaurant.domain.usecases

import ru.papino.restaurant.domain.repository.ProductTypesRepository
import ru.papino.restaurant.domain.repository.models.ProductTypesResponse

internal class GetProductTypesUseCase(private val repository: ProductTypesRepository) {

    suspend operator fun invoke(): ProductTypesResponse =
        repository.request()

}