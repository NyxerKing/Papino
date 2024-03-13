package ru.papino.restaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.room.RoomDependencies
import ru.papino.restaurant.databinding.ActivityRestaurantBinding
import ru.papino.restaurant.presentation.basket.views.BasketFragment
import ru.papino.restaurant.presentation.menu.views.MenuFragment
import ru.papino.restaurant.presentation.profile.views.ProfileFragment
import ru.papino.uikit.components.navigation.NavigationItem

class RestaurantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        RoomDependencies.init(applicationContext)
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clearDatabase()

        initObserver()
        initNavigation()
    }

    private fun clearDatabase() {
        lifecycleScope.launch {
            RoomDependencies.basketRepository.clear()
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            RoomDependencies.basketRepository.changeBasket.collect(::basketChange)
        }
    }

    private fun initNavigation() {
        with(binding) {
            val onClick = { item: NavigationItem ->
                navigation.setSelected(item)
                replaceFragment(item)
            }

            navigation.set(
                onClickMenu = onClick,
                onClickBasket = onClick,
                onClickProfile = onClick
            )

            onClick(NavigationItem.MENU)
        }
    }

    private suspend fun basketChange(id: Int) {
        val count = RoomDependencies.basketRepository.getCountAll()
        binding.navigation.setBasketCount(count)
    }

    private fun replaceFragment(itemNavigation: NavigationItem) {
        val fragment = when (itemNavigation) {
            NavigationItem.MENU -> {
                MenuFragment()
            }

            NavigationItem.BASKET -> {
                BasketFragment()
            }

            NavigationItem.PROFILE -> {
                ProfileFragment()
            }
        }

        val fragmentManager = supportFragmentManager

        val currentFragment = fragmentManager.findFragmentByTag(ACTIVE_FRAGMENT)
        if (currentFragment != null && currentFragment::class.java == fragment::class.java) {
            return
        }

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, ACTIVE_FRAGMENT)
        fragmentTransaction.commit()

        setBackground(itemNavigation)
    }

    private fun setBackground(item: NavigationItem) {
        if (item == NavigationItem.PROFILE) {
            binding.root.background = ResourcesCompat.getDrawable(
                resources,
                ru.papino.uikit.R.drawable.bg_rect_base,
                binding.root.context.theme
            )
        } else {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    ru.papino.uikit.R.color.backgroundMenuColor
                )
            )
        }
    }

    companion object {
        private const val ACTIVE_FRAGMENT = "ACTIVE_FRAGMENT"

        fun newIntent(context: Context?) = Intent(context, RestaurantActivity::class.java)
    }
}