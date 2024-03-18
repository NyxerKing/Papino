package ru.papino.uikit.components.cards

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.card.MaterialCardView
import ru.papino.uikit.R
import ru.papino.uikit.components.Button

/**
 * TODO
 *
 * @constructor
 * TODO
 *
 * @param context
 * @param attrs
 * @param defStyleAttr
 */
class CardMenuBase @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.component_card_menu, this)
    }

    private val imagePreview = findViewById<ImageView>(R.id.imagePreview)
    private val textTitle = findViewById<TextView>(R.id.textTitle)
    private val textSubtitle = findViewById<TextView>(R.id.textSubtitle)
    private val textPrice = findViewById<TextView>(R.id.textPrice)
    private val textPriceCount = findViewById<TextView>(R.id.textPriceCount)
    private val buttonAddCart = findViewById<Button>(R.id.buttonAddCart)
    private val shimmerImage = findViewById<ShimmerFrameLayout>(R.id.shimmerImagePreview)

    init {
        this.radius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            CORNER_RADIUS,
            context.resources.displayMetrics
        )

        setCardBackgroundColor(resources.getColor(R.color.backgroundCardColor, context.theme))
        strokeWidth = 0

        layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun showShimmer() {
        shimmerImage.startShimmer()
        shimmerImage.visibility = VISIBLE
        imagePreview.visibility = INVISIBLE
    }

    fun hideShimmer() {
        shimmerImage.stopShimmer()
        shimmerImage.visibility = GONE
        imagePreview.visibility = VISIBLE
    }

    fun setType(type: MenuButtonType) {
        var buttonText: String
        var buttonColor: Int
        when (type) {
            MenuButtonType.BASE -> {
                buttonText = resources.getString(ru.papino.uikit.R.string.add_to_cart)
                buttonColor = ru.papino.uikit.R.color.backgroundButtonColor
            }

            MenuButtonType.BASKET -> {
                buttonText = resources.getString(ru.papino.uikit.R.string.to_cart)
                buttonColor = ru.papino.uikit.R.color.backgroundButtonGreyMedium
            }
        }

        buttonAddCart.text = buttonText
        buttonAddCart.setBackgroundColor(resources.getColor(buttonColor, context.theme))
    }

    /**
     * Установить параметры
     *
     * @param imageRes картинка из ресурсов
     * @param imageUrl картинка из ссылки
     * @param imageDrawable картинка из drawable
     * @param title заголовок
     * @param subtitle описание
     * @param price цена
     * @param priceCount текст о измерении и сумме
     * @param buttonText текст на кнопке
     * @param buttonColor фон кнопки
     * @param buttonOnClick коллбэк на кнопку
     */
    fun set(
        @DrawableRes imageRes: Int? = null,
        imageUrl: String? = null,
        imageDrawable: Drawable? = null,
        title: String? = null,
        subtitle: String? = null,
        price: String? = null,
        priceCount: String? = null,
        buttonText: String? = null,
        @ColorRes buttonColor: Int? = null,
        buttonOnClick: (() -> Unit)? = null,
    ) {
        imageUrl?.let {
            imagePreview.setImageURI(Uri.parse(it))
        }

        imageRes?.let {
            imagePreview.setImageResource(it)
        }

        imageDrawable?.let {
            imagePreview.setImageDrawable(it)
        }

        title?.let { textTitle.text = it }
        subtitle?.let { textSubtitle.text = it }
        price?.let { textPrice.text = it }
        priceCount?.let { textPriceCount.text = it }

        buttonText?.let { buttonAddCart.text = it }

        buttonColor?.let { colorInt ->
            buttonAddCart.setBackgroundColor(resources.getColor(colorInt, context.theme))
        }

        buttonOnClick?.let { click -> buttonAddCart.setOnClickListener { click() } }
    }

    companion object {
        private const val CORNER_RADIUS = 10f
    }
}

enum class MenuButtonType {
    BASE,
    BASKET
}