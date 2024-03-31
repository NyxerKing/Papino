package ru.papino.restaurant.presentation.basket.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.papino.restaurant.extensions.toPrice
import ru.papino.restaurant.presentation.basket.models.BasketUIModel
import ru.papino.uikit.components.cards.CardBaskedComponent
import ru.papino.uikit.components.controllers.ElementControllerComponent

internal class BasketAdapter :
    RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    private var data: List<BasketUIModel>? = null
    private var deleteOnClick: ((BasketUIModel) -> Unit)? = null
    private var minusOnClick: ((BasketUIModel) -> Unit)? = null
    private var plusOnClick: ((BasketUIModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BasketViewHolder(CardBaskedComponent(parent.context))

    override fun getItemCount() = data?.size ?: 0

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        data?.let { list ->
            holder.set(list[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun set(
        list: List<BasketUIModel>,
        deleteOnClick: ((BasketUIModel) -> Unit)? = null,
        minusOnClick: ((BasketUIModel) -> Unit)? = null,
        plusOnClick: ((BasketUIModel) -> Unit)? = null
    ) {
        data = list
        this.deleteOnClick = deleteOnClick
        this.minusOnClick = minusOnClick
        this.plusOnClick = plusOnClick
        notifyDataSetChanged()
    }

    inner class BasketViewHolder(private val view: CardBaskedComponent) :
        RecyclerView.ViewHolder(view) {

        fun set(data: BasketUIModel) {
            view.set(
                CardBaskedComponent.Data(
                    title = data.name,
                    price = view.resources.getString(
                        ru.papino.uikit.R.string.insert_sum_count,
                        data.price
                    ),
                    priceAll = view.resources.getString(
                        ru.papino.uikit.R.string.insert_count_sum_all,
                        data.count.toString(),
                        (data.price.toPrice() * data.count).toPrice()
                    ),
                    controllerItemData = ElementControllerComponent.Data(
                        count = data.count,
                        deleteOnClick = { deleteOnClick?.invoke(data) },
                        minusOnClick = { minusOnClick?.invoke(data) },
                        plusOnClick = { plusOnClick?.invoke(data) }
                    )
                )
            )
        }
    }
}