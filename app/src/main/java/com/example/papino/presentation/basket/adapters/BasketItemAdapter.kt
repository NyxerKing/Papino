package com.example.papino.presentation.basket.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.papino.databinding.ItemFoodBasketBinding
import com.example.papino.presentation.basket.model.FoodBasketModel


class BasketItemAdapter(
    list: List<FoodBasketModel>,
    val onClickDeleteItem: ((item : FoodBasketModel) -> Unit)
) : RecyclerView.Adapter<BasketItemAdapter.BasketItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBasketBinding.inflate(inflater, parent, false)
        return BasketItemViewHolder(binding)
    }

    private val data: MutableList<FoodBasketModel> = list.toMutableList()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: BasketItemViewHolder, position: Int) {
        val food = data[position]
        with(holder.binding) {

            itemNameBasket.text = food.name
            itemtvSizeBasket.text = "см: ${food.size} цена: ${food.price}"
            itemDetailsFood.text = food.detailsFood
            cover.load(food.uriImageFood)
            imageView2.setOnClickListener { deleteItemFoodBasket(food) }
        }
    }

    private fun deleteItemFoodBasket(item: FoodBasketModel) {
        onClickDeleteItem.invoke(item)
        data.remove(item)
        notifyDataSetChanged()
    }


    class BasketItemViewHolder(val binding: ItemFoodBasketBinding) :
        RecyclerView.ViewHolder(binding.root)
}
