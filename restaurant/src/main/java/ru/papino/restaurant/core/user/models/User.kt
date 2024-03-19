package ru.papino.restaurant.core.user.models

internal data class User(
    val id: Long,
    val firstName: String,
    val secondName: String,
    val phone: String,
    val address: String
)
