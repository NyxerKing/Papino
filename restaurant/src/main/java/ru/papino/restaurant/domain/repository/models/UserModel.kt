package ru.papino.restaurant.domain.repository.models


internal data class UserModel(
    val firstName: String,
    val secondName: String,
    val phone: String,
    val address: String,
    val password: String
)