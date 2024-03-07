package ru.papino.uikit.components.cards

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import ru.papino.uikit.R
import ru.papino.uikit.components.controllers.ElementControllerComponent

class CardBaskedComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int
) : MaterialCardView(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.component_card_basket, this)
    }

    private val textTitle = findViewById<TextView>(R.id.textTitle)
    private val textPrice = findViewById<TextView>(R.id.textPrice)
    private val textPriceAll = findViewById<TextView>(R.id.textPriceAll)
    private val controllerItem = findViewById<ElementControllerComponent>(R.id.controllerItem)

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
}