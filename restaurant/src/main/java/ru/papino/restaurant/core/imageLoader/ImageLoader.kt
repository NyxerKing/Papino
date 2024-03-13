package ru.papino.restaurant.core.imageLoader

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView

interface ImageLoader {
    fun loadImage(context: Context, view: ImageView, link: String)

    fun getDrawable(context: Context, link: String, onResource: (Drawable?) -> Unit)
}