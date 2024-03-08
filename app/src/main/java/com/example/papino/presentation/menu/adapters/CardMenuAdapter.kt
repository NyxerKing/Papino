package com.example.papino.presentation.menu.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.papino.net.Food
import com.example.papino.net.ListFood
import ru.papino.uikit.components.cards.CardMenuBase

class CardMenuAdapter(private val onClickItem: (Food) -> Unit) :
    RecyclerView.Adapter<CardMenuAdapter.AdapterHolder>() {

    private var listMenu: ListFood? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        return AdapterHolder(CardMenuBase(parent.context))
    }

    override fun getItemCount(): Int = listMenu?.group?.size ?: 0

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
        val item = listMenu?.group?.get(position)
        item?.let { food ->
            holder.set(food = food)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun set(list: ListFood?) {
        listMenu = list
        notifyDataSetChanged()
    }

    inner class AdapterHolder(private val cardMenu: CardMenuBase) :
        RecyclerView.ViewHolder(cardMenu) {
        fun set(food: Food) {
            cardMenu.set(
                imageRes = ru.papino.uikit.R.drawable.pizza_pepperoni_2,
                title = food.namefood,
                subtitle = food.detailsfood,
                price = food.pricefood,
                buttonOnClick = {
                    onClickItem(food)
                    cardMenu.set(
                        buttonColor = ru.papino.uikit.R.color.backgroundButtonGreyMedium
                    )
                }
            )
        }
    }
}