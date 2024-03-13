package ru.papino.restaurant.domain.repository

import kotlinx.coroutines.flow.SharedFlow
import ru.papino.restaurant.domain.repository.models.ProductModel

internal interface BasketRepository {

    suspend fun getAll(): List<ProductModel>

    suspend fun getCountAll(): Int

    suspend fun getCount(id: Int): Int

    suspend fun add(product: ProductModel)

    suspend fun delete(product: ProductModel)

    suspend fun update(id: Int, count: Int)

    suspend fun clear()

    val changeBasket: SharedFlow<Int>
}