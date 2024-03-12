package com.example.papino.presentation.basket.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.papino.presentation.basket.mappers.BasketMapper
import com.example.papino.presentation.basket.model.FoodBasketUI
import ru.papino.uikit.components.cards.CardBaskedComponent

class CardBasketAdapter(private val mapper: BasketMapper) :
    RecyclerView.Adapter<CardBasketAdapter.CardBasketViewHolder>() {

    private var data: List<FoodBasketUI>? = null
    private var deleteOnClick: ((FoodBasketUI) -> Unit)? = null
    private var minusOnClick: ((FoodBasketUI) -> Unit)? = null
    private var plusOnClick: ((FoodBasketUI) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardBasketViewHolder(CardBaskedComponent(parent.context))

    override fun getItemCount() = data?.size ?: 0

    override fun onBindViewHolder(holder: CardBasketViewHolder, position: Int) {
        data?.let { list ->
            holder.set(list[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun set(
        list: List<FoodBasketUI>,
        deleteOnClick: ((FoodBasketUI) -> Unit)? = null,
        minusOnClick: ((FoodBasketUI) -> Unit)? = null,
        plusOnClick: ((FoodBasketUI) -> Unit)? = null
    ) {
        data = list
        this.deleteOnClick = deleteOnClick
        this.minusOnClick = minusOnClick
        this.plusOnClick = plusOnClick
        notifyDataSetChanged()
    }

    inner class CardBasketViewHolder(private val view: CardBaskedComponent) :
        RecyclerView.ViewHolder(view) {

        fun set(data: FoodBasketUI) {
            view.set(
                mapper.toComponentData(
                    model = data,
                    deleteOnClick = { deleteOnClick?.invoke(data) },
                    minusOnClick = { minusOnClick?.invoke(data) },
                    plusOnClick = { plusOnClick?.invoke(data) }
                )
            )
        }
    }
}