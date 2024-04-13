package ru.papino.uikit.components.inputs.watchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

internal class MaskTextWatcher(
    private val editText: EditText,
    private val mask: String,
    private val pattern: String
) : TextWatcher {

    private var isInsideChange = false
    private var prevValue = ""
    private var onReturnPreviousValue: ((prevValue: String) -> Unit)? = null

    fun setOnReturnPreviousValue(action: (prevValue: String) -> Unit) {
        onReturnPreviousValue = action
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        if (isInsideChange) return
        isInsideChange = true
        prevValue = s.toString()

        val newLength = after + s.length
        if (newLength > mask.length || newLength < getClearLengthMask()) {
            setText(s.toString())
        }

        isInsideChange = false
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (isInsideChange) return
        isInsideChange = true

        val regex = Regex(pattern)
        if (!regex.containsMatchIn(s)) {
            setText(prevValue)
        }

        isInsideChange = false
    }

    override fun afterTextChanged(s: Editable) {}

    private fun setText(s: String) {
        editText.setText(s)
        onReturnPreviousValue?.invoke(s)
    }

    private fun getClearMask() = mask.replace(EMPTY_CHAR, "")

    private fun getClearLengthMask() = getClearMask().length

    companion object {
        const val EMPTY_CHAR = "_"
    }
}