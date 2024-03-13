package ru.papino.restaurant.core.imageLoader

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class ImageLoaderImpl : ImageLoader {
    override fun loadImage(context: Context, view: ImageView, link: String) {
        Glide.with(context)
            .load(link)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(view)
    }

    override fun getDrawable(context: Context, link: String, onResource: (Drawable?) -> Unit) {
        Glide.with(context)
            .asDrawable()
            .load(link)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    onResource(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    onResource(placeholder)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    onResource(errorDrawable)
                }
            })
    }
}