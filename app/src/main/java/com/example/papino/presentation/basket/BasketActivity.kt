package com.example.papino.presentation.basket

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.papino.core.sharedPref.CoreSharedPreferences
import com.example.papino.databinding.AcitivityBasketBinding
import com.example.papino.presentation.basket.adapters.BasketItemAdapter
import com.example.papino.presentation.basket.model.FoodBasketModel
import ru.papino.uikit.components.navigation.NavigationItem


class BasketActivity : AppCompatActivity() {

    private lateinit var binding: AcitivityBasketBinding
    private lateinit var coreSP: CoreSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        initFullMode()
        super.onCreate(savedInstanceState)
        coreSP = CoreSharedPreferences(context = this)

        binding = AcitivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            navigation.setSelected(NavigationItem.BASKET)
            navigation.set(
                onClickMenu = { onBackPressed() },
                onClickProfile = {
                    // todo профиль
                }
            )
            basketRecyclerView.setLayoutManager(LinearLayoutManager(this@BasketActivity))

            coreSP.getBasket().let { pack ->
                pack?.dataList?.let { list ->
                    initUI(basketFoods = list)
                    binding.basketRecyclerView.adapter =
                        BasketItemAdapter(list, ::onItemClickDelete)
                } ?: run { showError() }
            }
        }
    }

    private fun initFullMode() {
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
    }

    private fun showError() {
        val toast = Toast.makeText(
            applicationContext,
            "Ваша корзина пуста. Перейдите в меню",
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }


    private fun onItemClickDelete(item: FoodBasketModel) {
        coreSP.getBasket()?.apply {
            dataList.remove(item)
        }?.run {
            coreSP.setBasket(this)
            initUI(basketFoods = this.dataList)
        }
    }

    private fun initUI(basketFoods: List<FoodBasketModel>) {
        with(binding) {
            navigation.setBasketCount(basketFoods.size)

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
                // todo оформить заказ
            }
        }
    }
}
