package ru.papino.restaurant.core.imageLoader

import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

internal class DrawableTarget(
    private val onResource: ((Drawable?) -> Unit)?
) :
    CustomTarget<Drawable>() {

    override fun onResourceReady(
        resource: Drawable,
        transition: Transition<in Drawable>?
    ) {
        onResource?.invoke(resource)
    }

    override fun onLoadCleared(placeholder: Drawable?) {
        onResource?.invoke(placeholder)
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        onResource?.invoke(errorDrawable)
    }
}