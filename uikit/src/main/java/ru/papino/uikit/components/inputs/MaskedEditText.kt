package ru.papino.uikit.components.inputs

import android.content.Context
import android.graphics.Rect
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText
import ru.papino.uikit.components.inputs.watchers.MaskTextWatcher

open class MaskedEditText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attributeSet, defStyleAttr) {

    private var _mask = ""
    private var _isClick = false

    fun setMask(mask: String, pattern: String) {
        _mask = mask
        setText(getClearMask())
        addTextChangedListener(createWatcher(_mask, pattern))
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        _isClick = focused
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
    }

    override fun onTextChanged(
        text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int
    ) {
        _isClick = false
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        if (_isClick) {
            val endIndex = getClearMask().length
            setSelection(endIndex)
        } else {
            super.onSelectionChanged(selStart, selEnd)
        }
    }

    private fun createWatcher(mask: String, pattern: String): TextWatcher {
        val watcher = MaskTextWatcher(this, mask, pattern)
        watcher.setOnReturnPreviousValue { value -> setSelection(value.length) }
        return watcher
    }

    private fun getClearMask() = _mask.replace(MaskTextWatcher.EMPTY_CHAR, "")
}