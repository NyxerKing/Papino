package ru.papino.restaurant.domain.usecases

import ru.papino.restaurant.domain.repository.UserRepository
import ru.papino.restaurant.domain.response.UserResponse

internal class GetUserByPasswordUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(login: String, password: String): UserResponse =
        userRepository.getUserByPassword(login, password)
}