package ru.papino.restaurant.data.datasource.net.models

import com.google.gson.annotations.SerializedName

internal data class MenuJsonModel(
    @SerializedName("group") val products: List<ProductJsonModel>
)