package ru.papino.restaurant.domain.usecases

import ru.papino.restaurant.domain.repository.UserRepository
import ru.papino.restaurant.domain.response.UserResponse

internal class UpdateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        id: Long,
        firstName: String?,
        secondName: String?,
        address: String?
    ): UserResponse = userRepository.update(id, firstName, secondName, address)
}