package ru.papino.uikit.constants

import ru.papino.uikit.R

enum class OrderStatus {
    CREATED {
        override fun getStringResource() = R.string.order_status_created
    },
    DELIVERED {
        override fun getStringResource() = R.string.order_status_delivered
    },
    COMPLETED {
        override fun getStringResource() = R.string.order_status_completed
    };

    abstract fun getStringResource(): Int
}