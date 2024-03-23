package ru.papino.uikit.components.cards

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import ru.papino.uikit.R
import ru.papino.uikit.constants.OrderStatus

class CardOrderComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.component_order, this)
    }

    private val textViewOrderNumber = findViewById<TextView>(R.id.textViewOrderNumber)
    private val textViewOrderDate = findViewById<TextView>(R.id.textViewOrderDate)
    private val textViewOrderList = findViewById<TextView>(R.id.textViewOrderList)
    private val textViewSumOrder = findViewById<TextView>(R.id.textViewSumOrder)
    private val textViewStatus = findViewById<TextView>(R.id.textViewStatus)

    init {
        background = ResourcesCompat.getDrawable(resources, R.drawable.bg_rect_10, context.theme)
        backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.backgroundCardColor, context.theme))
        setPadding(resources.getDimensionPixelSize(R.dimen.d_14))
        layoutParams =
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
    }

    fun set(
        number: Long,
        dateCreate: String,
        products: List<String>,
        sum: String,
        status: OrderStatus
    ) {
        textViewOrderNumber.text = resources.getString(R.string.order_number, number.toString())

        textViewOrderDate.text = dateCreate

        val list = StringBuilder()
        products.forEach {
            list.appendLine(it)
        }
        textViewOrderList.text = list.toString()
        textViewSumOrder.text = sum
        textViewStatus.text = resources.getString(status.getStringResource())
    }
}