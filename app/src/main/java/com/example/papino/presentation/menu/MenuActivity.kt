package com.example.papino.presentation.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.papino.DBTest
import com.example.papino.R
import com.example.papino.SharedKeys
import com.example.papino.databinding.ActivityMenuBinding
import com.example.papino.net.Food
import com.example.papino.net.ListFood
import com.example.papino.presentation.basket.BasketActivity
import com.example.papino.presentation.basket.model.FoodBasketModel
import com.example.papino.presentation.menu.adapters.CardMenuAdapter
import com.example.papino.presentation.menu.models.PackFoodBaskedModel
import com.example.papino.presentation.regestration.controlles.ControllerFood
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private var foods: ListFood? = null

    private val adapterMenu = CardMenuAdapter(::updateBasket)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        with(binding)
        {
            setContentView(root)

            imgBasket.setOnClickListener {
                val intent = Intent(this@MenuActivity, BasketActivity::class.java)
                startActivity(intent)
            }

            buttonBackActivityMenu.setOnClickListener {
                onBackPressed()
            }
            menuRecycler.setLayoutManager(GridLayoutManager(this@MenuActivity, 1))
            menuRecycler.adapter = adapterMenu

            val itemDecorator = DividerItemDecoration(root.context, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(
                root.context,
                R.drawable.divider_vertical
            )?.let { drawable ->
                itemDecorator.setDrawable(drawable)
            }
            menuRecycler.addItemDecoration(itemDecorator)
        }

        getFood()
        initTabs()
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = this@MenuActivity.getSharedPreferences(
            SharedKeys.BASKED_DATA_KEY,
            Context.MODE_PRIVATE
        )

        val pack: PackFoodBaskedModel? =
            Gson().fromJson(
                sharedPreferences.getString(SharedKeys.BASKED_ITEM_KEY, ""),
                PackFoodBaskedModel::class.java
            )

        if (pack != null) {
            binding.tvBasketCount.text = "+ ${pack.dataList.size}"
        }
    }

    private fun updateBasket(foodVars: Food) {
        addBasket(foodVars)
    }

    private fun addBasket(foodVars: Food) {
        val sharedPreferences = this@MenuActivity.getSharedPreferences(
            SharedKeys.BASKED_DATA_KEY,
            Context.MODE_PRIVATE
        )
        var pack: PackFoodBaskedModel? = null
        val json = sharedPreferences.getString(SharedKeys.BASKED_ITEM_KEY, "")
        if (json?.isNotEmpty() == true) {
            pack = Gson().fromJson(json, PackFoodBaskedModel::class.java)
            pack.dataList.add(
                FoodBasketModel(
                    id = foodVars.id!!,
                    name = foodVars.namefood!!,
                    detailsFood = foodVars.detailsfood!!,
                    size = foodVars.sizeportion!!,
                    price = foodVars.pricefood!!,
                    uriImageFood = foodVars.uriImageFood!!
                )
            )
        } else {
            pack = PackFoodBaskedModel(
                dataList = listOf(
                    FoodBasketModel(
                        id = foodVars.id!!,
                        name = foodVars.namefood!!,
                        detailsFood = foodVars.detailsfood!!,
                        size = foodVars.sizeportion!!,
                        price = foodVars.pricefood!!,
                        uriImageFood = foodVars.uriImageFood!!
                    )
                ).toMutableList()
            )
        }

        if (pack != null) {
            binding.tvBasketCount.text = "+ ${pack.dataList.size}"
        }

        val editJson = Gson().toJson(pack)
        val edit = sharedPreferences.edit()
        edit.putString(SharedKeys.BASKED_ITEM_KEY, editJson)
        edit.commit()
    }

    private fun initTabs() {
        with(binding) {
            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    Log.d("MenuActivity", "onTabSelected ${tab?.text ?: ""}")
                    changeTabs(tab?.text.toString())
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    //Log.d("MenuActivity", "onTabUnselected ${tab?.text ?: ""}")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    //Log.d("MenuActivity", "onTabReselected ${tab?.text ?: ""}")
                }

            })
        }
    }

    private fun changeTabs(typeFoodTab: String) {
        when (typeFoodTab) {
            resources.getString(TypeFood.pizza.getResourceId().toInt()) -> {
                //adapterMenu.set(list = DBTest.getDataTest())
                adapterMenu.set(list = getFoodToFilter(typeFood = TypeFood.pizza.getFasetFoodName()))
            }

            resources.getString(TypeFood.burger.getResourceId().toInt()) -> {
                adapterMenu.set(list = getFoodToFilter(typeFood = TypeFood.burger.getFasetFoodName()))
            }

            resources.getString(TypeFood.salad.getResourceId().toInt()) -> {
                adapterMenu.set(list = getFoodToFilter(typeFood = TypeFood.salad.getFasetFoodName()))
            }

            resources.getString(TypeFood.shashlik.getResourceId().toInt()) -> {
                adapterMenu.set(list = getFoodToFilter(typeFood = TypeFood.shashlik.getFasetFoodName()))
            }

            resources.getString(TypeFood.sendwich.getResourceId().toInt()) -> {
                adapterMenu.set(list = getFoodToFilter(typeFood = TypeFood.sendwich.getFasetFoodName()))
            }

            resources.getString(TypeFood.snack.getResourceId().toInt()) -> {
                adapterMenu.set(list = getFoodToFilter(typeFood = TypeFood.snack.getFasetFoodName()))
            }

            /* resources.getString(TypeFood.drink.getResourceId().toInt()) -> {
                adapterMenu.set(list = getFoodToFilter(typeFood = TypeFood.drink.getFasetFoodName()))
            }*/

            resources.getString(TypeFood.bread.getResourceId().toInt()) -> {
                adapterMenu.set(list = getFoodToFilter(typeFood = TypeFood.bread.getFasetFoodName()))
            }

            resources.getString(TypeFood.soup.getResourceId().toInt()) -> {
                adapterMenu.set(list = getFoodToFilter(typeFood = TypeFood.soup.getFasetFoodName()))
            }
        }
    }

    private fun getFoodToFilter(typeFood: String): ListFood? {
        val list = foods?.group?.filter { food -> food.typeFoodid == typeFood }
        list?.let {
            if (it.isNotEmpty())
                return ListFood(group = it)
        }
        return foods
    }


    @Throws(Exception::class)
    fun getFood() {
        val controller = ControllerFood { listFood ->
            foods = listFood
            setContentView(binding.root)

            with(binding) {
                changeTabs(getString(R.string.tab_menu_pizza))
            }
        }
        controller.start()
    }
}
