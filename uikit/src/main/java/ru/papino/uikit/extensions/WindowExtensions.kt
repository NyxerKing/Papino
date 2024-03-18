package ru.papino.uikit.extensions

import android.os.Build
import android.view.View
import android.view.Window

fun Window.fullscreen(value: Boolean = true) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
        this.setDecorFitsSystemWindows(value)
    } else {
        val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        val view = this.decorView
        val vis = view.systemUiVisibility
        view.systemUiVisibility = if (value) vis and flags.inv() else vis or flags
    }
}