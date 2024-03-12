package ru.papino.uikit.components.cards

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import ru.papino.uikit.R
import ru.papino.uikit.components.controllers.ElementControllerComponent

class CardBaskedComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.component_card_basket, this)
    }

    private val textTitle = findViewById<TextView>(R.id.textTitle)
    private val textPrice = findViewById<TextView>(R.id.textPrice)
    private val textPriceAll = findViewById<TextView>(R.id.textPriceAll)
    private val controllerItem = findViewById<ElementControllerComponent>(R.id.controllerItem)

    init {
        setCardBackgroundColor(resources.getColor(R.color.backgroundCardColor, context.theme))
        radius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            CORNER_RADIUS,
            context.resources.displayMetrics
        )
        strokeWidth = 0
        layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun set(
        data: Data? = null
    ) {
        data?.run {
            title?.let { textTitle.text = it }
            price?.let { textPrice.text = it }
            priceAll?.let { textPriceAll.text = it }
            controllerItemData?.let { controllerItem.set(data = it) }
        }
    }

    data class Data(
        val title: String? = null,
        val price: String? = null,
        val priceAll: String? = null,
        val controllerItemData: ElementControllerComponent.Data? = null,
    )

    companion object {
        private const val CORNER_RADIUS = 10f
    }
}