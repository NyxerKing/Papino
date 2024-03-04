package ru.papino.uikit.components

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import ru.papino.uikit.R

/**
 * Кнопка для PaPino
 *
 * @constructor
 * TODO
 *
 * @param context
 * @param attrs
 * @param defStyleAttr
 */
open class Button @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = R.style.PaPino_Button
) :
    MaterialButton(context, attrs, defStyleAttr) {
}