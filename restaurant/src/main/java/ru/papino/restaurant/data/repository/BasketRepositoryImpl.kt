package ru.papino.restaurant.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import ru.papino.restaurant.core.statuses.ActionStatus
import ru.papino.restaurant.data.datasource.room.dao.BasketDao
import ru.papino.restaurant.data.datasource.room.mappers.RoomBasketMapper
import ru.papino.restaurant.domain.repository.BasketRepository
import ru.papino.restaurant.domain.repository.models.ProductModel
import ru.papino.restaurant.domain.repository.models.status.BasketActionStatus

internal class BasketRepositoryImpl(
    private val basketDao: BasketDao,
    private val roomBasketMapper: RoomBasketMapper
) : BasketRepository {

    private val _changeBasket = MutableSharedFlow<BasketActionStatus>()
    override val changeBasket: SharedFlow<BasketActionStatus> = _changeBasket.asSharedFlow()

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
            _changeBasket.emit(BasketActionStatus(product.id, ActionStatus.ADD))
        }
    }

    override suspend fun delete(id: Int) {
        withContext(Dispatchers.IO) {
            basketDao.delete(id)
            _changeBasket.emit(BasketActionStatus(id, ActionStatus.DELETE))
        }
    }

    override suspend fun update(id: Int, count: Int) {
        withContext(Dispatchers.IO) {
            basketDao.update(id, count)
            _changeBasket.emit(BasketActionStatus(id, ActionStatus.UPDATE))
        }
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            basketDao.clear()
            _changeBasket.emit(BasketActionStatus(null, ActionStatus.CLEAR))
        }
    }
}