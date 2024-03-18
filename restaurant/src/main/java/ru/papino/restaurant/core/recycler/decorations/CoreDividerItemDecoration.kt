package ru.papino.restaurant.core.recycler.decorations

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import ru.papino.restaurant.R

class CoreDividerItemDecoration(
    context: Context,
    orientation: Int = VERTICAL
) :
    DividerItemDecoration(context, orientation) {

    init {
        ContextCompat.getDrawable(
            context,
            R.drawable.divider_vertical
        )?.let { drawable ->
            setDrawable(drawable)
        }
    }
}