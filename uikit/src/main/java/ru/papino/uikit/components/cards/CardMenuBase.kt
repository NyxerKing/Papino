package ru.papino.uikit.components.cards

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
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
    attrs: AttributeSet,
    defStyleAttr: Int
) : MaterialCardView(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.component_card_menu, this)
    }

    private val imagePreview = findViewById<ImageView>(R.id.imagePreview)
    private val textTitle = findViewById<TextView>(R.id.textTitle)
    private val textSubtitle = findViewById<TextView>(R.id.textSubtitle)
    private val textPrice = findViewById<TextView>(R.id.textPrice)
    private val buttonAddCart = findViewById<Button>(R.id.buttonAddCart)

    /**
     * Установить параметры
     *
     * @param imageRes картинка из ресурсов
     * @param imageUrl картинка из ссылки
     * @param title заголовок
     * @param subtitle описание
     * @param price цена
     * @param onClick коллбэк на кнопку
     */
    fun set(
        @DrawableRes imageRes: Int? = null,
        imageUrl: String? = null,
        title: String? = null,
        subtitle: String? = null,
        price: String? = null,
        onClick: (() -> Unit)? = null,
    ) {
        imageRes?.let {
            imagePreview.setImageResource(it)
        } ?: run {
            imageUrl?.let {
                imagePreview.setImageURI(Uri.parse(it))
            }
        }

        title?.let { textTitle.text = it }
        subtitle?.let { textSubtitle.text = it }
        price?.let { textPrice.text = it }

        onClick?.let { click -> buttonAddCart.setOnClickListener { click() } }
    }
}