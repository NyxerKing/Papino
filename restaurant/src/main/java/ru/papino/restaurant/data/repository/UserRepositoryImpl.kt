package ru.papino.restaurant.data.repository

import ru.papino.restaurant.core.user.di.UserDI.BONUS_DEFAULT
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
    override suspend fun create(user: UserModel): UserResponse {
        val service = netDS.getRetrofit().create(UserService::class.java)
        try {
            val result = service.createUser(
                secondName = user.secondName,
                firstName = user.firstName,
                phone = user.phone,
                password = user.password,
                bonus = BONUS_DEFAULT,
                address = user.address
            ).execute()
            result.body()?.let {
                return mapper.toResponse(it)
            }
        } catch (ex: Exception) {
            return UserResponse.Error("error create user")
        }
        return UserResponse.Error("error create user")
    }

    override suspend fun getUserByToken(token: String): UserResponse {
        val service = netDS.getRetrofit().create(UserService::class.java)
        try {
            val result = service.getUserByToken(token).execute()
            result.body()?.let {
                return mapper.toResponse(it)
            }
        } catch (ex: Exception) {
            return UserResponse.Error("error create user")
        }
        return UserResponse.Error("error create user")
    }

    override suspend fun getUserByPassword(login: String, password: String): UserResponse {
        val service = netDS.getRetrofit().create(UserService::class.java)
        try {
            val result = service.getUser(login, password).execute()
            result.body()?.let {
                return mapper.toResponse(it)
            }
        } catch (ex: Exception) {
            return UserResponse.Error("error create user")
        }
        return UserResponse.Error("error create user")
    }

    override suspend fun update(
        id: Long,
        firstName: String?,
        secondName: String?,
        address: String?
    ): UserResponse {
        val service = netDS.getRetrofit().create(UserService::class.java)
        try {
            val result = service.updateUser(id, secondName, firstName, address).execute()
            result.body()?.let {
                return mapper.toResponse(it)
            }
        } catch (ex: Exception) {
            return UserResponse.Error("error update user")
        }
        return UserResponse.Error("error update user")
    }

    companion object {
        fun getInstance() = UserRepositoryImpl(NetDataSource.getInstance(), UserMapper())
    }
}