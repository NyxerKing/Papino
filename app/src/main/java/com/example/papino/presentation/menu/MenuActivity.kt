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
import com.example.papino.R
import com.example.papino.core.sharedPref.CoreSharedPreferences
import com.example.papino.core.sharedPref.SharedKeys
import com.example.papino.data.datasource.local.LocalDataSource
import com.example.papino.data.datasource.net.NetDataSource
import com.example.papino.data.repository.MenuRepository
import com.example.papino.databinding.ActivityMenuBinding
import com.example.papino.net.ListFood
import com.example.papino.presentation.basket.BasketActivity
import com.example.papino.presentation.mappers.FoodMapper
import com.example.papino.presentation.menu.adapters.CardMenuAdapter
import com.example.papino.presentation.menu.models.FoodUI
import com.example.papino.presentation.menu.models.PackFoodBaskedModel
import com.example.papino.presentation.regestration.controlles.ControllerFood
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private var foods: ListFood? = null

    private val mapperFood = FoodMapper()
    private val adapterMenu = CardMenuAdapter(::updateBasket)
    private lateinit var coreSP: CoreSharedPreferences
    private lateinit var menuRepository: MenuRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coreSP = CoreSharedPreferences(context = this)
        menuRepository = MenuRepository(
            localDS = LocalDataSource.getInstance(resources),
            netDS = NetDataSource.getInstance()
        )

        requestWindowFeature(Window.FEATURE_NO_TITLE)
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

        //getFood()


        //todo временное получение данныъ из json
        initLocalMenu()
        initTabs()
    }

    override fun onStart() {
        super.onStart()
        updateUI()
    }

    private fun updateBasket(foodVars: FoodUI) {
        addBasket(foodVars)
    }

    private fun addBasket(foodVars: FoodUI) {
        val sharedPreferences = this@MenuActivity.getSharedPreferences(
            SharedKeys.BASKED_DATA_KEY,
            Context.MODE_PRIVATE
        )
        var pack: PackFoodBaskedModel? = null
        val json = sharedPreferences.getString(SharedKeys.BASKED_ITEM_KEY, "")
        if (json?.isNotEmpty() == true) {
            pack = Gson().fromJson(json, PackFoodBaskedModel::class.java)
            pack.dataList.add(mapperFood.toBasketUI(foodUI = foodVars))
        } else {
            pack = PackFoodBaskedModel(
                dataList = listOf(mapperFood.toBasketUI(foodUI = foodVars)).toMutableList()
            )
        }

        if (pack != null) {
            binding.tvBasketCount.text = "+ ${pack.dataList.size}"
        }

        val editJson = Gson().toJson(pack)
        val edit = sharedPreferences.edit()
        edit.putString(SharedKeys.BASKED_ITEM_KEY, editJson)
        edit.commit()

        foodVars.isInBasket = true
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
        val list = when (typeFoodTab) {
            resources.getString(TypeFood.pizza.getResourceId()) -> {
                getFoodToFilter(typeFood = TypeFood.pizza.getFasetFoodName())
            }

            resources.getString(TypeFood.burger.getResourceId()) -> {
                getFoodToFilter(typeFood = TypeFood.burger.getFasetFoodName())
            }

            resources.getString(TypeFood.salad.getResourceId()) -> {
                getFoodToFilter(typeFood = TypeFood.salad.getFasetFoodName())
            }

            resources.getString(TypeFood.shashlik.getResourceId()) -> {
                getFoodToFilter(typeFood = TypeFood.shashlik.getFasetFoodName())
            }

            resources.getString(TypeFood.sendwich.getResourceId()) -> {
                getFoodToFilter(typeFood = TypeFood.sendwich.getFasetFoodName())
            }

            resources.getString(TypeFood.snack.getResourceId()) -> {
                getFoodToFilter(typeFood = TypeFood.snack.getFasetFoodName())
            }

            resources.getString(TypeFood.bread.getResourceId()) -> {
                getFoodToFilter(typeFood = TypeFood.bread.getFasetFoodName())
            }

            resources.getString(TypeFood.soup.getResourceId()) -> {
                getFoodToFilter(typeFood = TypeFood.soup.getFasetFoodName())
            }

            else -> null
        }

        list?.let {
            adapterMenu.set(mapperFood.toUI(it))
            updateUI()
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
            changeTabs(getString(R.string.tab_menu_pizza))
        }
        controller.start()
    }

    private fun initLocalMenu() {
        foods = menuRepository.getMenu()
        changeTabs(getString(R.string.tab_menu_pizza))
    }

    private fun updateUI() {
        coreSP.getBasket()
            ?.let { basket ->
                binding.tvBasketCount.text = "+ ${basket.dataList.size}"

                adapterMenu.checkInBasket(
                    listBasketId = mapperFood.toUI(basket).map { foodUI -> foodUI.id }
                )
            }
    }
}
