package ru.papino.restaurant.extensions

fun Double.toPrice() = this.toString().removeSuffix(".0")