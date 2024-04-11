package ru.papino.restaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
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
import ru.papino.restaurant.data.di.RepositoryManager
import ru.papino.restaurant.databinding.ActivityRestaurantBinding
import ru.papino.restaurant.domain.response.AboutResponse
import ru.papino.restaurant.domain.status.BasketActionStatus
import ru.papino.restaurant.domain.usecases.GetAboutUseCase
import ru.papino.restaurant.domain.usecases.GetUserByTokenUseCase
import ru.papino.restaurant.extensions.switchFragment
import ru.papino.uikit.extensions.showAlert

class RestaurantActivity : AppCompatActivity() {

    private val viewModel by viewModels<RestaurantViewModel> {
        RestaurantViewModelFactory(
            getAboutUseCase = GetAboutUseCase(aboutRepository = RepositoryManager().getAboutRepository()),
            getUserByTokenUseCase = GetUserByTokenUseCase(userRepository = RepositoryManager().getUserRepository())
        )
    }

    private lateinit var binding: ActivityRestaurantBinding
    private lateinit var badge: BadgeDrawable

    private val screenManager = ScreenManager.getManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        RoomDependencies.init(applicationContext)
        viewModel.authorizationByToken()
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clearDatabase()

        initNavigation()
        initObserver()
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

        lifecycleScope.launch {
            UserDI.onInitUser.collect(::updateNavigation)
        }

        lifecycleScope.launch {
            viewModel.about.collect(::checkIsClose)
        }
    }

    private fun updateNavigation(user: User?) {
        updateNavigation(user != null)
        if (user != null) viewModel.loadAbout()
    }

    private fun updateNavigation(isOpenOrders: Boolean) {
        binding.navigation.menu.findItem(R.id.orders).let { itemOrder ->
            itemOrder.isEnabled = isOpenOrders
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

                    R.id.about -> {
                        replaceFragment(screenManager.aboutFragment)
                        true
                    }

                    else -> false
                }
            }

            replaceFragment(screenManager.menuFragment)
        }
    }

    private suspend fun basketChange(basketStatus: BasketActionStatus) {
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

    private fun checkIsClose(response: AboutResponse) {
        when (response) {
            is AboutResponse.Success -> {
                if (response.data.isClose) showMessage(resources.getString(R.string.error_request_message_about))
            }

            is AboutResponse.Error -> {
                showMessage(resources.getString(R.string.error_request_message_about))
            }
        }
    }

    private fun showMessage(message: String) {
        binding.root.context.showAlert(
            title = resources.getString(R.string.warning_title),
            message = message
        )
    }

    companion object {
        const val ACTIVE_FRAGMENT = "ACTIVE_FRAGMENT"

        fun newIntent(context: Context?) = Intent(context, RestaurantActivity::class.java)
    }
}