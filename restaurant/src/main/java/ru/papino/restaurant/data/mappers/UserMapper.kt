package ru.papino.restaurant.data.mappers

import ru.papino.restaurant.core.user.models.Token
import ru.papino.restaurant.core.user.models.User
import ru.papino.restaurant.data.datasource.net.models.UserJsonModel
import ru.papino.restaurant.domain.repository.models.UserResponse

internal class UserMapper {

    fun toResponse(data: UserJsonModel) = UserResponse.Success(
        user = User(
            firstName = data.firstName,
            secondName = data.secondName,
            phone = data.phone,
            middleName = "",
            address = ""
        ),
        token = Token(token = data.token)
    )
}