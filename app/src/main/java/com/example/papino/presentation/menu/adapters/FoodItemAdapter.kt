package com.example.papino.presentation.menu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.papino.R
import com.example.papino.databinding.ItemFoodBinding
import com.example.papino.net.Food
import com.example.papino.net.ListFood

class FoodItemAdapter(val onClickItem: ((Food) -> Unit)) :
    RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder>() {

    private var data: ListFood? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindind = ItemFoodBinding.inflate(inflater, parent, false)
        return FoodItemViewHolder(bindind)
    }

    override fun getItemCount(): Int = data?.group?.size ?: 0

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val item = data?.group?.get(position)
        with(holder.binding) {
            item?.let { food ->
                name.text = food.namefood
                detailsFood.text = food.detailsfood
                cover.load(food.uriImageFood)

                if (food.sizeportion == "")
                {
                    tvSize.text = "цена: ${food.pricefood}"
                }
                else
                { tvSize.text = "${food.sizeportion} см / цена: ${food.pricefood}" }
                root.setOnClickListener { onClickItem.invoke(food) }
            }
        }
    }

    fun set(list: ListFood?) {
        data = list
        notifyDataSetChanged()
    }

    class FoodItemViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root)
}