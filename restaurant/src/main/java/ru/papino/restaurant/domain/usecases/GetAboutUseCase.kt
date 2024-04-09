package ru.papino.restaurant.domain.usecases

import ru.papino.restaurant.domain.repository.AboutRepository

internal class GetAboutUseCase(
    private val aboutRepository: AboutRepository
) {
    suspend operator fun invoke() = aboutRepository.getAbout()
}