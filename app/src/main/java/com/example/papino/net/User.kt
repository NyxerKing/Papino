package com.example.papino.net

data class User(
    val id : String? = null,
    val surname: String? = null,
    var name: String? = null,
    val telephoneNumber: String? = null,
    val password: String? = null,
    val bonus: String? = null,
    val token: String? = null
)

data class ListUser (
    val group: List<User>
)