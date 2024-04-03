package ru.papino.restaurant.data.datasource.net.models

import com.google.gson.annotations.SerializedName

internal data class ProductTypesJsonModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("typeFood")
    val typeValue: String,
    @SerializedName("nameFood")
    val name: String,
)
