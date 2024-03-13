package ru.papino.restaurant.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import ru.papino.restaurant.data.datasource.room.dao.BasketDao
import ru.papino.restaurant.data.datasource.room.mappers.RoomBasketMapper
import ru.papino.restaurant.domain.repository.BasketRepository
import ru.papino.restaurant.domain.repository.models.ProductModel

internal class BasketRepositoryImpl(
    private val basketDao: BasketDao,
    private val roomBasketMapper: RoomBasketMapper
) : BasketRepository {

    private val _changeBasket = MutableSharedFlow<Int>()
    override val changeBasket: SharedFlow<Int> = _changeBasket.asSharedFlow()

    override suspend fun getAll(): List<ProductModel> {
        return withContext(Dispatchers.IO) {
            roomBasketMapper.toDomain(basketDao.getAll())
        }
    }

    override suspend fun getCountAll(): Int {
        return withContext(Dispatchers.IO) {
            basketDao.getCountAll()
        }
    }

    override suspend fun getCount(id: Int): Int {
        return withContext(Dispatchers.IO) {
            basketDao.getCount(id)
        }
    }

    override suspend fun add(product: ProductModel) {
        withContext(Dispatchers.IO) {
            basketDao.insert(roomBasketMapper.toEntity(product))
            _changeBasket.emit(product.id ?: 0)
        }
    }

    override suspend fun delete(product: ProductModel) {
        withContext(Dispatchers.IO) {
            basketDao.delete(roomBasketMapper.toEntity(product))
            _changeBasket.emit(product.id ?: 0)
        }
    }

    override suspend fun update(id: Int, count: Int) {
        withContext(Dispatchers.IO) {
            basketDao.update(id, count)
            _changeBasket.emit(id)
        }
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            basketDao.clear()
            _changeBasket.emit(0)
        }
    }
}