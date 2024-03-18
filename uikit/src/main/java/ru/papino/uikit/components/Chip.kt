package ru.papino.uikit.components

import android.content.Context

class Chip<T>(
    context: Context
) : com.google.android.material.chip.Chip(context) {

    private var model: T? = null

    fun setModel(data: T) {
        model = data
    }

    fun getModel() = model
}