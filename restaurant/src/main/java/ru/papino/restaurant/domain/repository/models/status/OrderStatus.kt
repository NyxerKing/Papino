package ru.papino.restaurant.domain.repository.models.status

import com.google.gson.annotations.SerializedName

internal enum class OrderStatus {
    @SerializedName("create")
    CREATED,

    @SerializedName("working")
    WORKING,

    @SerializedName("delivery")
    DELIVERED,

    @SerializedName("completed")
    COMPLETED,

    @SerializedName("cancel")
    CANCELED
}