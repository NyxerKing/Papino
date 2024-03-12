package com.example.papino.presentation.recycler.decorations

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.papino.R

class MenuDividerItemDecoration(context: Context, orientation: Int) :
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