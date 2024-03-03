package com.example.papino.presentation.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.papino.SharedKeys
import com.example.papino.databinding.ActivityMenuBinding
import com.example.papino.net.Food
import com.example.papino.net.ListFood
import com.example.papino.presentation.basket.BasketActivity
import com.example.papino.presentation.basket.model.FoodBasketModel
import com.example.papino.presentation.menu.adapters.FoodItemAdapter
import com.example.papino.presentation.menu.models.PackFoodBaskedModel
import com.example.papino.presentation.regestration.controlles.ControllerFood
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import java.util.logging.Logger

//var count = 0;

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    private lateinit var listFoodToAdapter: ListFood

    private val adapterMenu = FoodItemAdapter(::updateBasket)

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
        setContentView(binding.root)

        binding.imgBasket.setOnClickListener {
            val intent = Intent(this@MenuActivity, BasketActivity::class.java)
            startActivity(intent)
        }

        binding.buttonBackActivityMenu.setOnClickListener {
            onBackPressed()
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
                    detailsFood = "Test 3",
                    size = foodVars.sizeportion!!,
                    price = foodVars.pricefood!!
                )
            )
        } else {
            pack = PackFoodBaskedModel(
                dataList = listOf<FoodBasketModel>(
                    FoodBasketModel(
                        id = foodVars.id!!,
                        name = foodVars.namefood!!,
                        detailsFood = "Test 3",
                        size = foodVars.sizeportion!!,
                        price = foodVars.pricefood!!
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
                    //adapterMenu.set(list = )
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    Log.d("MenuActivity", "onTabUnselected ${tab?.text ?: ""}")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    Log.d("MenuActivity", "onTabReselected ${tab?.text ?: ""}")
                }

            })
        }
    }


    @Throws(Exception::class)
    fun getFood() {
        val controller = ControllerFood() {
            listFoodToAdapter = it
            setContentView(binding.root)

            with(binding) {
                menuRecycler.setLayoutManager(GridLayoutManager(this@MenuActivity, 1));
                menuRecycler.adapter = adapterMenu
                adapterMenu.set(list = listFoodToAdapter)
            }
        }
        controller.start()
    }
}
