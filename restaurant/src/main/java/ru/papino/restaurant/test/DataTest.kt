package ru.papino.restaurant.test

import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.core.user.models.User
import ru.papino.restaurant.domain.repository.models.OrderModel
import ru.papino.restaurant.domain.repository.models.status.OrderStatus
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.random.Random

// todo удалить
internal object DataTest {
    fun getOrders(): List<OrderModel> {
        val orders = mutableListOf<OrderModel>()
        repeat(10) {
            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
            val current = formatter.format(time)

            orders.add(
                OrderModel(
                    id = it.toLong() + 1,
                    created = current,
                    products = listOf("Пицца", "Бургер"),
                    sum = Random(it).nextDouble(10000.0, 50000.0),
                    status = if (Random(it).nextInt(0, 10) > 5)
                        OrderStatus.CREATED
                    else if (Random(it).nextInt(0, 10) <= 3)
                        OrderStatus.DELIVERED else OrderStatus.COMPLETED
                )
            )
        }

        return orders
    }

    fun initUser() {
        UserDI.init(
            User(
                id = 1,
                firstName = "Alexandr",
                secondName = "Терехин",
                phone = "+7 937 036 25 00",
                address = "Братская 21 55"
            )
        )
    }
}