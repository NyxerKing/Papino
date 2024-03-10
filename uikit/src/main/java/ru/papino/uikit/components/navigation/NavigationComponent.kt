package ru.papino.uikit.components.navigation

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import ru.papino.uikit.R

class NavigationComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.component_navigation, this)
    }

    private val menuView = findViewById<TextView>(R.id.menuView)
    private val basketView = findViewById<TextView>(R.id.basketView)
    private val profileView = findViewById<TextView>(R.id.profileView)

    init {
        background =
            ResourcesCompat.getDrawable(resources, R.drawable.bg_rect_corder_top_15, context.theme)

        val dimen10 = resources.getDimensionPixelSize(R.dimen.d_10)
        val dimen30 = resources.getDimensionPixelSize(R.dimen.d_30)
        setPadding(dimen10, dimen10, dimen10, dimen30)
    }

    fun set(
        onClickMenu: (() -> Unit)? = null,
        onClickBasket: (() -> Unit)? = null,
        onClickProfile: (() -> Unit)? = null
    ) {
        onClickMenu?.let { click -> menuView.setOnClickListener { click() } }
        onClickBasket?.let { click -> basketView.setOnClickListener { click() } }
        onClickProfile?.let { click -> profileView.setOnClickListener { click() } }
    }

    fun setBasketCount(count: Int) {
        basketView.text = resources.getString(R.string.basket_count_text, count.toString())
    }

    fun setSelected(itemSelect: NavigationItem) {
        val view = when (itemSelect) {
            NavigationItem.MENU -> menuView

            NavigationItem.BASKET -> basketView

            NavigationItem.PROFILE -> profileView
        }

        view.setTextAppearance(R.style.PaPino_TextAppearances_Navigation_Text_Selected)
        TextViewCompat.setCompoundDrawableTintList(
            view,
            ColorStateList.valueOf(resources.getColor(R.color.backgroundButtonColor, context.theme))
        )
    }
}