package com.example.papino.presentation.basket

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.papino.App
import com.example.papino.core.sharedPref.SharedKeys
import com.example.papino.databinding.AcitivityBasketBinding
import com.example.papino.presentation.basket.adapters.BasketItemAdapter
import com.example.papino.presentation.basket.model.FoodBasketModel
import com.example.papino.presentation.menu.models.PackFoodBaskedModel
import com.google.gson.Gson


class BasketActivity : AppCompatActivity() {


    private lateinit var binding: AcitivityBasketBinding

    private lateinit var sharedPreferences: SharedPreferences
    private var dataBasket: PackFoodBaskedModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = this@BasketActivity.getSharedPreferences(
            SharedKeys.BASKED_DATA_KEY,
            Context.MODE_PRIVATE
        )
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        binding = AcitivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val liner = LinearLayoutManager(this@BasketActivity)
            basketRecyclerView.setLayoutManager(liner);
        }


        Gson().fromJson(
            sharedPreferences.getString(SharedKeys.BASKED_ITEM_KEY, ""),
            PackFoodBaskedModel::class.java
        ).let { pack ->
            dataBasket = pack
            dataBasket?.dataList?.let { list ->
                initUI(basketFoods = list)
                binding.basketRecyclerView.adapter = BasketItemAdapter(list, ::onItemClickDelete)
            } ?: run {
                val toast = Toast.makeText(
                    applicationContext,
                    "Ваша корзина пуста. Перейдите в меню",
                    Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }

        binding.buttonBackActivityMenu.setOnClickListener {
            onBackPressed()
        }
    }


    private fun onItemClickDelete(item: FoodBasketModel) {
        val packFoodBask: PackFoodBaskedModel? =
            Gson().fromJson(
                sharedPreferences.getString(SharedKeys.BASKED_ITEM_KEY, ""),
                PackFoodBaskedModel::class.java
            )

        if (packFoodBask != null) {
            packFoodBask.dataList.remove(item)
            val editJson = Gson().toJson(packFoodBask)
            val edit = sharedPreferences.edit()
            edit.putString(SharedKeys.BASKED_ITEM_KEY, editJson)
            edit.commit()
        }
    }

    private fun initUI(basketFoods: List<FoodBasketModel>) {
        with(binding) {
            titleOrderFood.text =
                resources.getString(
                    ru.papino.uikit.R.string.insert_products,
                    basketFoods.size.toString()
                )

            titleOrderFoodSum.text = resources.getString(
                ru.papino.uikit.R.string.insert_sum,
                basketFoods.sumOf { food -> food.price.replace(" ", "").toInt() }.toString()
            )

            //todo скидки нет
            titleOrderDiscountSum.text = resources.getString(
                ru.papino.uikit.R.string.insert_sum,
                "0"
            )

            toPaySum.text = resources.getString(
                ru.papino.uikit.R.string.insert_sum,
                basketFoods.sumOf { food -> food.price.replace(" ", "").toInt() }.toString()
            )

            buttonCheckout.setOnClickListener {
                 val user : App? = null
                 user?.getUser()
            }
        }
    }
}
