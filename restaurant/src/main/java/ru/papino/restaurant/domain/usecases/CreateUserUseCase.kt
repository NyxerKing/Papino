package ru.papino.restaurant.domain.usecases

import ru.papino.restaurant.domain.models.UserModel
import ru.papino.restaurant.domain.repository.UserRepository
import ru.papino.restaurant.domain.response.UserResponse

internal class CreateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: UserModel): UserResponse = userRepository.create(user)
}