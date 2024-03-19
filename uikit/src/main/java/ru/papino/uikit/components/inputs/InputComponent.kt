package ru.papino.uikit.components.inputs

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.textfield.TextInputLayout
import ru.papino.uikit.R

class InputComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.component_input, this)
    }

    private val textInputLayout = findViewById<TextInputLayout>(R.id.inputLayout)

    fun set() {

    }
}