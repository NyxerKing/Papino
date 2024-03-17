package ru.papino.restaurant.domain.usecases

import ru.papino.restaurant.domain.repository.UserRepository
import ru.papino.restaurant.domain.repository.models.UserModel
import ru.papino.restaurant.domain.repository.models.UserResponse

internal class CreateUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(user: UserModel): UserResponse = userRepository.create(user)
}