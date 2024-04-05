package ru.papino.restaurant.domain.usecases

import ru.papino.restaurant.domain.repository.UserRepository
import ru.papino.restaurant.domain.repository.models.UserResponse

internal class UpdateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        secondName: String?,
        firstName: String?,
        address: String?
    ): UserResponse = userRepository.update(secondName, firstName, address)
}