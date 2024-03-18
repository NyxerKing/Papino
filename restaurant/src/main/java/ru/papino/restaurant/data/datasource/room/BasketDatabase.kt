package ru.papino.restaurant.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.papino.restaurant.data.datasource.room.dao.BasketDao
import ru.papino.restaurant.data.datasource.room.models.BasketProductEntity

@Database(entities = [BasketProductEntity::class], version = 1)
internal abstract class BasketDatabase : RoomDatabase() {
    abstract fun basketDao(): BasketDao
}