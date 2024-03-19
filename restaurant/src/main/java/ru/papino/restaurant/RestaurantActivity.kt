package ru.papino.restaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.badge.BadgeDrawable
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.room.RoomDependencies
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.core.user.models.User
import ru.papino.restaurant.databinding.ActivityRestaurantBinding
import ru.papino.restaurant.extensions.switchFragment
import ru.papino.uikit.extensions.fullscreen

class RestaurantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantBinding
    private lateinit var badge: BadgeDrawable

    private val screenManager = ScreenManager.getManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        RoomDependencies.init(applicationContext)
        window.fullscreen()
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clearDatabase()

        initNavigation()
        initObserver()

        UserDI.init(
            User(
                id = 1,
                firstName = "Alexandr",
                secondName = "Терехин",
                phone = "+7 937 036 25 00",
                address = "Братская 21 55"
            )
        )
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
            badge = navigation.getOrCreateBadge(R.id.basket)
            badge.isVisible = true
            badge.number = 0

            navigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu -> {
                        replaceFragment(screenManager.menuFragment)
                        true
                    }

                    R.id.basket -> {
                        replaceFragment(screenManager.basketFragment)
                        true
                    }

                    R.id.orders -> {
                        replaceFragment(screenManager.ordersFragment)
                        true
                    }

                    R.id.profile -> {
                        val fragment = if (UserDI.isUserInitializer()) {
                            screenManager.profileFragment
                        } else {
                            screenManager.authorizationFragment
                        }
                        replaceFragment(fragment, true)
                        true
                    }

                    else -> false
                }
            }

            replaceFragment(screenManager.menuFragment)
        }
    }

    private suspend fun basketChange(id: Int) {
        val count = RoomDependencies.basketRepository.getCountAll()
        badge.number = count
    }

    private fun replaceFragment(fragment: Fragment, isDark: Boolean = false) {
        switchFragment(fragment)

        setBackground(isDark)
    }

    private fun setBackground(isDarkBackground: Boolean) {
        if (isDarkBackground) {
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