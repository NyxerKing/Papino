package ru.papino.restaurant.data.repository

import ru.papino.restaurant.data.datasource.net.impl.NetDataSource
import ru.papino.restaurant.data.datasource.net.services.UserService
import ru.papino.restaurant.data.mappers.UserMapper
import ru.papino.restaurant.domain.repository.UserRepository
import ru.papino.restaurant.domain.repository.models.UserModel
import ru.papino.restaurant.domain.repository.models.UserResponse

internal class UserRepositoryImpl(
    private val netDS: NetDataSource,
    private val mapper: UserMapper
) : UserRepository {
    override fun create(user: UserModel): UserResponse {
        val service = netDS.getRetrofit().create(UserService::class.java)
        service.createUser(
            secondName = user.secondName,
            firstName = user.firstName,
            phone = user.phone,
            password = user.password
        )
            .execute()
            .body()
            ?.let {
                return mapper.toResponse(it)
            } ?: return UserResponse.Error("error create user")
    }

    override fun getUserByToken(token: String): UserResponse {
        TODO("Not yet implemented")
    }

    override fun getUserByPassword(login: String, password: String): UserResponse {
        TODO("Not yet implemented")
    }
}