package ru.papino.restaurant.domain.usecases

import ru.papino.restaurant.core.user.models.Token
import ru.papino.restaurant.domain.repository.UserRepository
import ru.papino.restaurant.domain.repository.models.UserResponse

internal class GetUserByTokenUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(token: Token): UserResponse =
        userRepository.getUserByToken(token.token)
}