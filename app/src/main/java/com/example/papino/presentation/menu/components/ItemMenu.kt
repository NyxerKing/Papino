package com.example.papino.presentation.menu.components

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.papino.R
import com.google.android.material.chip.Chip

class ItemMenu @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    init {
        inflate(context, R.layout.item_food, this)
    }

    private val _cover = findViewById<ImageView>(R.id.cover)
    private val _name = findViewById<TextView>(R.id.name)
    private val _foodSize = findViewById<TextView>(R.id.tvSize)

    fun set(
        url: String? = null,
        name: String? = null,
        foodSize: String? = null
    ) {
        url?.let { _cover.setImageURI(Uri.EMPTY) }
        _name.text = name
        _foodSize.text = foodSize
    }
}