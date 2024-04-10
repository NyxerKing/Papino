package ru.papino.restaurant.data.datasource.net.models

import com.google.gson.annotations.SerializedName

internal data class AboutJsonModel(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("aboutOrganization")
    val aboutOrganization: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("workTimeFrom")
    val workTimeFrom: String,
    @SerializedName("workTimeTo")
    val workTimeTo: String,
    @SerializedName("isClose")
    val isClose: Boolean
)
