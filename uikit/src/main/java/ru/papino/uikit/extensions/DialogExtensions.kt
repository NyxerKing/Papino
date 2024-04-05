package ru.papino.uikit.extensions

import android.content.Context
import ru.papino.uikit.dialogs.AlertDialog

fun Context.showAlert(
    title: String,
    message: String,
    onClick: () -> Unit
) {
    AlertDialog(
        context = this,
        title = title,
        message = message,
        onClick = onClick
    ).show()
}