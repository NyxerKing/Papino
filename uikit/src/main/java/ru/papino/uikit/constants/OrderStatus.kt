package ru.papino.uikit.constants

import ru.papino.uikit.R

enum class OrderStatus {
    CREATED {
        override fun getStringResource() = R.string.order_status_created
    },
    WORKING {
        override fun getStringResource() = R.string.order_status_working
    },
    DELIVERED {
        override fun getStringResource() = R.string.order_status_delivered
    },
    COMPLETED {
        override fun getStringResource() = R.string.order_status_completed
    },
    CANCELED {
        override fun getStringResource() = R.string.order_status_canceled
    };

    abstract fun getStringResource(): Int
}