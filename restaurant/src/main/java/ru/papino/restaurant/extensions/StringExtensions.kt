package ru.papino.restaurant.extensions

internal fun String.toPrice(): Double = this.replace(" ", "").toDoubleOrNull() ?: 0.0