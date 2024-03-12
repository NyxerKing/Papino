package ru.papino.uikit.components.controllers

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ru.papino.uikit.R
import ru.papino.uikit.components.Button

class ElementControllerComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.component_element_controller, this)
    }

    private val buttonDelete = findViewById<Button>(R.id.buttonDelete)
    private val buttonMinus = findViewById<Button>(R.id.buttonMinus)
    private val textCount = findViewById<TextView>(R.id.textCount)
    private val buttonPlus = findViewById<Button>(R.id.buttonPlus)

    fun set(
        data: Data? = null
    ) {
        data?.run {
            count?.let { textCount.text = it.toString() }

            deleteOnClick?.let { buttonDelete.setOnClickListener { it() } }

            minusOnClick?.let { buttonMinus.setOnClickListener { it() } }

            plusOnClick?.let { buttonPlus.setOnClickListener { it() } }
        }

    }

    data class Data(
        val count: Int? = null,
        val deleteOnClick: (() -> Unit)? = null,
        val minusOnClick: (() -> Unit)? = null,
        val plusOnClick: (() -> Unit)? = null,
    )
}