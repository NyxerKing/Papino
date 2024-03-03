package com.example.papino.net

data class User(
    val id : String? = null,
    val surname: String? = null,
    var name: String? = null,
    val patronymic: String? = null,
    val telephoneNumber: String? = null,
    val password: String? = null
)

data class ListUser (
    val group: ArrayList<User>
)