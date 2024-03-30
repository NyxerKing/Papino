package ru.papino.restaurant.data.datasource.net.models

import com.google.gson.annotations.SerializedName

data class UserJsonModel(
    @SerializedName("id") val id: String,
    @SerializedName("surname") val secondName: String,
    @SerializedName("name") val firstName: String,
    @SerializedName("telephoneNumber") val phone: String,
    @SerializedName("password") val password: String,
    @SerializedName("bonus") val bonus: String,
    @SerializedName("address") val address: String,
    @SerializedName("dataCreated") val created: String,
    @SerializedName("token") val token: String,
    @SerializedName("errorMessage") val error: String?,
)
