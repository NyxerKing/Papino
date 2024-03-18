package ru.papino.restaurant.data.datasource.net.models

import com.google.gson.annotations.SerializedName

data class TokenJsonModel(
    @SerializedName("token") val token: String
)
