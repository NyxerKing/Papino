package ru.papino.restaurant.domain.usecases

import ru.papino.restaurant.domain.repository.UserRepository
import ru.papino.restaurant.domain.repository.models.TokenResponse
import ru.papino.restaurant.domain.repository.models.UserModel

internal class CreateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: UserModel): TokenResponse = userRepository.create(user)
}