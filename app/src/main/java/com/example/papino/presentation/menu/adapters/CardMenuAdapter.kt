package com.example.papino.presentation.menu.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.example.papino.presentation.menu.models.FoodUI
import ru.papino.uikit.components.cards.CardMenuBase

class CardMenuAdapter(private val onClickItem: (FoodUI) -> Unit) :
    RecyclerView.Adapter<CardMenuAdapter.AdapterHolder>() {

    private var elements: List<FoodUI>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        return AdapterHolder(CardMenuBase(parent.context))
    }

    override fun getItemCount(): Int = elements?.size ?: 0

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
        val item = elements?.get(position)
        item?.let { food ->
            holder.set(food = food)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun set(list: List<FoodUI>?) {
        elements = list
        notifyDataSetChanged()
    }

    /**
     * Проверить наличие продукта в корзине
     *
     * @param listBasketId
     */
    @SuppressLint("NotifyDataSetChanged")
    fun checkInBasket(listBasketId: List<Int>) {
        elements?.forEach { foodUI ->
            foodUI.isInBasket = listBasketId.any { id -> id == foodUI.id }
        }
        notifyDataSetChanged()
    }

    inner class AdapterHolder(private val cardMenu: CardMenuBase) :
        RecyclerView.ViewHolder(cardMenu) {
        fun set(food: FoodUI) {
            val imageLoader = cardMenu.context.imageLoader
            val request = ImageRequest.Builder(cardMenu.context)
                .data(food.linkCover)
                .target(onSuccess = { coverDrawable ->
                        initCard(food = food, drawable = coverDrawable)
                }, onStart = {
                    initCard(
                        food = food,
                        drawable = cardMenu.resources.getDrawable(ru.papino.uikit.R.drawable.pizza_pepperoni_2)
                    )
                    })
                .build()

            imageLoader.enqueue(request)
        }

        private fun initCard(food: FoodUI, drawable: Drawable) {
            with(cardMenu) {
                var buttonText = resources.getString(ru.papino.uikit.R.string.add_to_cart)
                var buttonColor = ru.papino.uikit.R.color.backgroundButtonColor

                if (food.isInBasket) {
                    buttonText = resources.getString(ru.papino.uikit.R.string.to_cart)
                    buttonColor = ru.papino.uikit.R.color.backgroundButtonGreyMedium
                }

                set(
                    imageDrawable = drawable,
                    title = "${food.name} ${food.sizePortion}",
                    subtitle = food.details,
                    price = food.price,
                    priceCount = cardMenu.resources.getString(ru.papino.uikit.R.string.count_text),
                    buttonText = buttonText,
                    buttonColor = buttonColor,
                    buttonOnClick = {
                        onClickItem(food)
                        initCard(food, drawable)
                    }
                )
            }
        }
    }
}