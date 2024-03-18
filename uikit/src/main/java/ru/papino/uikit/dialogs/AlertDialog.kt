package ru.papino.uikit.dialogs

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.papino.uikit.R

class AlertDialog(
    private val context: Context,
    private val title: String,
    private val message: String,
    private val onClick: () -> Unit
) {

    fun show() {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.dialog_button_positive) { _, _ -> onClick() }
            .show()
    }
}