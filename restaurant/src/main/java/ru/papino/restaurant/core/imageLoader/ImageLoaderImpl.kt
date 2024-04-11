package ru.papino.restaurant.core.imageLoader

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

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
            .into(DrawableTarget(onResource))
    }
}