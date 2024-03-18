package ru.papino.restaurant.domain.usecases

import ru.papino.restaurant.domain.repository.MenuRepository
import ru.papino.restaurant.domain.repository.models.MenuResponse

internal class GetMenuUseCase(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(): MenuResponse {
        return repository.request()
    }
}