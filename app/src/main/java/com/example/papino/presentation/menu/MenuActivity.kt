package com.example.papino.presentation.menu

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.papino.App
import com.example.papino.R
import com.example.papino.core.sharedPref.CoreSharedPreferences
import com.example.papino.data.datasource.local.LocalDataSource
import com.example.papino.data.datasource.net.NetDataSource
import com.example.papino.data.repository.MenuRepository
import com.example.papino.databinding.ActivityMenuBinding
import com.example.papino.net.ListFood
import com.example.papino.net.User
import com.example.papino.presentation.mappers.FoodMapper
import com.example.papino.presentation.menu.adapters.CardMenuAdapter
import com.example.papino.presentation.menu.models.FoodUI
import com.example.papino.presentation.menu.models.PackFoodBaskedModel
import com.example.papino.presentation.recycler.decorations.MenuDividerItemDecoration
import com.google.android.material.chip.Chip

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var coreSP: CoreSharedPreferences
    private lateinit var menuRepository: MenuRepository

    private val mapperFood = FoodMapper()
    private val adapterMenu = CardMenuAdapter(::updateBasket)

    private var foods: ListFood? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initFullMode()
        super.onCreate(savedInstanceState)
        coreSP = CoreSharedPreferences(context = this)
        menuRepository = MenuRepository(
            localDS = LocalDataSource.getInstance(resources),
            netDS = NetDataSource.getInstance()
        )

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding)
        {


        if (getUser()?.surname.isNullOrEmpty())
            {
                nameUserHello.text = ""
                countBonusUser.text = ""
            }
            else
            {
                nameUserHello.text = getUser()?.surname + " " + getUser()?.name
                countBonusUser.text = "Бонусы: " + getUser()?.bonus
            }


            menuRecycler.setLayoutManager(LinearLayoutManager(this@MenuActivity))
            menuRecycler.adapter = adapterMenu
            menuRecycler.addItemDecoration(
                MenuDividerItemDecoration(
                    root.context,
                    DividerItemDecoration.VERTICAL
                )
            )

            chipGroupMenu.setOnCheckedStateChangeListener { chipGroup, ints ->
                if (ints.isNotEmpty()) {
                    val index = ints.first()
                    val item = chipGroup.findViewById<Chip>(index)
                    val tab = item.text.toString()
                    changeTabs(typeFoodTab = tab)
                }
            }
        }

        initMenu()
    }

    override fun onStart() {
        super.onStart()
        updateUI(getString(R.string.tab_menu_pizza))
    }

    private fun initFullMode() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
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
    private fun updateBasket(foodVars: FoodUI) {
        addBasket(foodVars)
    }

    private fun addBasket(foodVars: FoodUI) {
        val pack =
            coreSP.getBasket()?.apply {
                dataList.add(mapperFood.toBasketUI(foodUI = foodVars))
            } ?: PackFoodBaskedModel(
                dataList = listOf(mapperFood.toBasketUI(foodUI = foodVars)).toMutableList()
            )

        coreSP.setBasket(data = pack)

        updateUI()
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
            updateUI(selectMenuItem = typeFoodTab)
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

    private fun initMenu() {
        menuRepository.getMenu(::initGetMenu, ::initGetMenu)
    }

    private fun initGetMenu(list: ListFood) {
        foods = list
        changeTabs(getString(R.string.tab_menu_pizza))
    }

    private fun updateUI(selectMenuItem: String? = null) {
        with(binding) {
            if (selectMenuItem?.isNotEmpty() == true) titleMenu.text = selectMenuItem
        }

        coreSP.getBasket()
            ?.let { basket ->


            adapterMenu.checkInBasket(
                    listBasketId = mapperFood.toUI(basket).map { foodUI -> foodUI.id }
                )
            }
    }

    private fun getUser(): User? =
        (application as App).getUser()
}
