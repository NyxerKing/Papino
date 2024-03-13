package ru.papino.restaurant.data.di

import android.content.res.Resources
import ru.papino.restaurant.data.datasource.local.impl.LocalDataSource
import ru.papino.restaurant.data.datasource.net.impl.NetDataSource
import ru.papino.restaurant.data.mappers.MenuMapper
import ru.papino.restaurant.data.repository.MenuRepositoryImpl
import ru.papino.restaurant.domain.repository.MenuRepository

internal class RepositoryManager(private val resources: Resources) {
    fun getInstance(): MenuRepository = MenuRepositoryImpl(
        localDS = LocalDataSource.getInstance(resource = resources),
        netDS = NetDataSource.getInstance(),
        mapper = MenuMapper()
    )
}