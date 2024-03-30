package ru.papino.uikit.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setText(text: String) = this.editText?.setText(text)