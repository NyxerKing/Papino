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
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.databinding.ActivityRestaurantBinding
import ru.papino.restaurant.extensions.switchFragment
import ru.papino.restaurant.presentation.authorization.views.AuthorizationFragment
import ru.papino.restaurant.presentation.basket.views.BasketFragment
import ru.papino.restaurant.presentation.menu.views.MenuFragment
import ru.papino.restaurant.presentation.profile.views.ProfileFragment
import ru.papino.uikit.components.navigation.NavigationItem
import ru.papino.uikit.extensions.fullscreen

class RestaurantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        RoomDependencies.init(applicationContext)
        window.fullscreen()
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
                if (UserDI.isUserInitializer()) {
                    ProfileFragment()
                } else {
                    AuthorizationFragment()
                }
            }
        }

        switchFragment(fragment)

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
        const val ACTIVE_FRAGMENT = "ACTIVE_FRAGMENT"

        fun newIntent(context: Context?) = Intent(context, RestaurantActivity::class.java)
    }
}