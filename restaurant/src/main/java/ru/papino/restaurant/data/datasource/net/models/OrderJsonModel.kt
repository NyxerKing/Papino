package ru.papino.restaurant.data.datasource.net.models

import com.google.gson.annotations.SerializedName
import ru.papino.restaurant.domain.repository.models.status.OrderStatus

internal data class OrderJsonModel(
    @SerializedName("orderId")
    val id: Long,
    @SerializedName("dateCreated")
    val created: String,
    @SerializedName("products")
    val products: List<OrderProductJsonModel>,
    @SerializedName("sum")
    val sum: Double,
    @SerializedName("status")
    val status: OrderStatus
)

internal data class OrderProductJsonModel(
    @SerializedName("name")
    val name: String
)
