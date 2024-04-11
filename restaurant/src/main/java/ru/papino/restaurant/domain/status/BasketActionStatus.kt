package ru.papino.restaurant.domain.status

import ru.papino.restaurant.core.statuses.ActionStatus

internal data class BasketActionStatus(
    val productId: Int?,
    val status: ActionStatus
)
