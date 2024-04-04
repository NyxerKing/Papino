package ru.papino.restaurant.presentation.orders.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.papino.restaurant.extensions.toPrice
import ru.papino.restaurant.presentation.orders.mappers.OrdersMapper
import ru.papino.restaurant.presentation.orders.models.OrderUIModel
import ru.papino.uikit.components.cards.CardOrderComponent

internal class OrdersAdapter(private val mapper: OrdersMapper) :
    RecyclerView.Adapter<OrdersAdapter.OrdersAdapterHolder>() {

    private var orders: MutableList<OrderUIModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersAdapterHolder {
        return OrdersAdapterHolder(CardOrderComponent(parent.context))
    }

    override fun getItemCount() = orders?.size ?: 0

    override fun onBindViewHolder(holder: OrdersAdapterHolder, position: Int) {
        val item = orders?.get(position)
        item?.let { holder.set(it) }
    }

    fun set(list: List<OrderUIModel>) {
        orders = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class OrdersAdapterHolder(private val orderView: CardOrderComponent) :
        RecyclerView.ViewHolder(orderView) {

        fun set(order: OrderUIModel) {
            orderView.set(
                number = order.id,
                dateCreate = order.created,
                products = order.products,
                sum = orderView.resources.getString(
                    ru.papino.uikit.R.string.insert_sum,
                    order.sum.toPrice()
                ),
                status = mapper.toStatusKit(order.status)
            )
        }
    }
}