package ru.papino.uikit.components.headers

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import ru.papino.uikit.R

class HeaderContainerComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.component_header_container, this)
    }

    private val container = findViewById<ConstraintLayout>(R.id.headerContainer)
    private val imageViewLogo = findViewById<ImageView>(R.id.imageViewLogo)
    private val textViewUserName = findViewById<TextView>(R.id.textViewUserName)
    private val textViewBonus = findViewById<TextView>(R.id.textViewBonus)

    init {
        layoutParams =
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                resources.getDimensionPixelSize(R.dimen.d_120)
            )

        container.setPadding(
            resources.getDimensionPixelSize(R.dimen.d_20),
            0,
            resources.getDimensionPixelSize(R.dimen.d_20),
            0
        )
    }

    fun set(
        userName: String? = null,
        bonus: String? = null,
        drawableBackground: Drawable? = null,
        drawableLogo: Drawable? = null
    ) {
        textViewUserName.text = userName
        textViewBonus.text = bonus

        drawableBackground?.let {
            container.background = it
        } ?: run {
            container.background =
                ResourcesCompat.getDrawable(resources, R.drawable.bg_rect_base, context.theme)
        }

        drawableLogo?.let {
            imageViewLogo.setImageDrawable(it)
        } ?: run {
            imageViewLogo.setImageResource(R.drawable.main_menu_logo)
        }
    }
}