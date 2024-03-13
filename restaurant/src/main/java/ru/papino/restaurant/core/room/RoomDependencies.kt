package ru.papino.restaurant.core.room

import android.content.Context
import androidx.room.Room
import ru.papino.restaurant.data.datasource.room.BasketDatabase
import ru.papino.restaurant.data.datasource.room.mappers.RoomBasketMapper
import ru.papino.restaurant.data.repository.BasketRepositoryImpl
import ru.papino.restaurant.domain.repository.BasketRepository

internal object RoomDependencies {
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: BasketDatabase by lazy {
        Room.databaseBuilder(applicationContext, BasketDatabase::class.java, "database.db")
            .createFromAsset("room_basket.db")
            .build()
    }

    val basketRepository: BasketRepository by lazy {
        BasketRepositoryImpl(
            appDatabase.basketDao(),
            RoomBasketMapper()
        )
    }
}