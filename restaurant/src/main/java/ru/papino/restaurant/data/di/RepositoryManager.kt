package ru.papino.restaurant.data.di

import ru.papino.restaurant.data.datasource.local.AboutLocalDataSource
import ru.papino.restaurant.data.datasource.net.impl.NetDataSource
import ru.papino.restaurant.data.mappers.AboutMapper
import ru.papino.restaurant.data.mappers.MenuMapper
import ru.papino.restaurant.data.mappers.OrdersMapper
import ru.papino.restaurant.data.repository.AboutRepositoryImpl
import ru.papino.restaurant.data.repository.MenuRepositoryImpl
import ru.papino.restaurant.data.repository.OrdersRepositoryImpl
import ru.papino.restaurant.data.repository.UserRepositoryImpl
import ru.papino.restaurant.domain.repository.AboutRepository
import ru.papino.restaurant.domain.repository.MenuRepository
import ru.papino.restaurant.domain.repository.OrdersRepository
import ru.papino.restaurant.domain.repository.UserRepository

internal class RepositoryManager {
    fun getMenuInstance(): MenuRepository = MenuRepositoryImpl(
        netDS = NetDataSource.getInstance(),
        mapper = MenuMapper()
    )

    fun getOrdersInstance(): OrdersRepository = OrdersRepositoryImpl(
        netDS = NetDataSource.getInstance(),
        mapper = OrdersMapper()
    )

    fun getAboutRepository(): AboutRepository = AboutRepositoryImpl(
        local = AboutLocalDataSource.getInstance(),
        netDS = NetDataSource.getInstance(),
        aboutMapper = AboutMapper()
    )

    fun getUserRepository(): UserRepository = UserRepositoryImpl.getInstance()
}