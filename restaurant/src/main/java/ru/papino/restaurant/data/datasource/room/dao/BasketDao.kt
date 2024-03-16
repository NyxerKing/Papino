package ru.papino.restaurant.data.datasource.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.papino.restaurant.data.datasource.room.models.BasketProductEntity

@Dao
internal interface BasketDao {

    @Query("SELECT * FROM baskets")
    fun getAll(): List<BasketProductEntity>

    @Query("SELECT SUM(count) FROM baskets")
    fun getCountAll(): Int

    @Query("SELECT count FROM baskets WHERE id = :id")
    fun getCount(id: Int): Int

    @Insert(entity = BasketProductEntity::class)
    fun insert(products: BasketProductEntity)

    @Query("DELETE FROM baskets WHERE id = :id")
    fun delete(id: Int)

    @Query("UPDATE baskets SET count = :count WHERE id = :id")
    fun update(id: Int, count: Int)

    @Query("DELETE FROM baskets")
    fun clear()
}