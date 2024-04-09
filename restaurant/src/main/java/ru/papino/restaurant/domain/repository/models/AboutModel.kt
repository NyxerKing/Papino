package ru.papino.restaurant.domain.repository.models

internal data class AboutModel(
    val id: Long,
    val name: String,
    val aboutOrganization: String,
    val latitude: String,
    val longitude: String,
)
