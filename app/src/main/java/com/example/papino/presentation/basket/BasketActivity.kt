package com.example.papino.presentation.basket

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.papino.core.sharedPref.CoreSharedPreferences
import com.example.papino.databinding.AcitivityBasketBinding
import com.example.papino.presentation.basket.adapters.CardBasketAdapter
import com.example.papino.presentation.basket.mappers.BasketMapper
import com.example.papino.presentation.basket.model.FoodBasketModel
import com.example.papino.presentation.basket.model.FoodBasketUI
import com.example.papino.presentation.menu.MenuActivity
import com.example.papino.presentation.recycler.decorations.MenuDividerItemDecoration
import com.example.papino.presentation.regestration.EnterUserActivity
import ru.papino.uikit.components.navigation.NavigationItem


class BasketActivity : AppCompatActivity() {

    private lateinit var binding: AcitivityBasketBinding
    private lateinit var coreSP: CoreSharedPreferences

    private var basketAdapter: CardBasketAdapter? = null
    private lateinit var basketMapper: BasketMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        initFullMode()
        super.onCreate(savedInstanceState)
        coreSP = CoreSharedPreferences(context = this)

        binding = AcitivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            navigation.setSelected(NavigationItem.BASKET)
            navigation.set(
                onClickMenu = {
                    val intent = Intent(this@BasketActivity, MenuActivity::class.java)
                    startActivity(intent)
                },
                onClickProfile = {
                    val intent = Intent(this@BasketActivity, EnterUserActivity::class.java)
                    startActivity(intent)
                }
            )
            basketMapper = BasketMapper(resources)
            basketAdapter = CardBasketAdapter(basketMapper)
            basketRecyclerView.adapter = basketAdapter
            basketRecyclerView.addItemDecoration(
                MenuDividerItemDecoration(
                    root.context,
                    DividerItemDecoration.VERTICAL
                )
            )

            coreSP.getBasket().let { pack ->
                pack?.dataList?.let { list ->
                    initUI(basketFoods = list)
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


    private fun onItemClickDelete(item: FoodBasketUI) {
        coreSP.getBasket()?.apply {
            dataList.removeAll { element -> element.id == item.id }
        }?.run {
            coreSP.setBasket(this)
            initUI(basketFoods = this.dataList)
        }
    }

    private fun onItemMinusClick(item: FoodBasketUI) {
        coreSP.getBasket()?.apply {
            val firstIndex = dataList.indexOfFirst { model -> model.id == item.id }
            dataList.removeAt(firstIndex)
        }?.run {
            coreSP.setBasket(this)
            initUI(basketFoods = this.dataList)
        }
    }

    private fun onItemPlusClick(item: FoodBasketUI) {
        coreSP.getBasket()?.apply {
            val firstIndex = dataList.indexOfFirst { model -> model.id == item.id }
            val copy = dataList[firstIndex].copy()
            dataList.add(copy)
        }?.run {
            coreSP.setBasket(this)
            initUI(basketFoods = this.dataList)
        }
    }

    private fun initUI(basketFoods: List<FoodBasketModel>) {
        basketAdapter?.set(
            list = basketMapper.toListUI(basketFoods),
            deleteOnClick = ::onItemClickDelete,
            minusOnClick = ::onItemMinusClick,
            plusOnClick = ::onItemPlusClick
        )

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
