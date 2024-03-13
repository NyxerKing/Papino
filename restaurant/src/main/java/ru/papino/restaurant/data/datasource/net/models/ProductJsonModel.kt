package ru.papino.restaurant.data.datasource.net.models

import com.google.gson.annotations.SerializedName

internal data class ProductJsonModel(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("namefood") val name: String? = null,
    @SerializedName("sizeportion") val size: String? = null,
    @SerializedName("pricefood") val price: String? = null,
    @SerializedName("detailsfood") val details: String? = null,
    @SerializedName("typeFoodid") val typeProduct: String? = null,
    @SerializedName("uriImageFood") val linkCover: String? = null
)
