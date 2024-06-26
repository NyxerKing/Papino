package ru.papino.restaurant

import android.util.Log
import ru.papino.restaurant.presentation.about.views.AboutFragment
import ru.papino.restaurant.presentation.authorization.views.AuthorizationFragment
import ru.papino.restaurant.presentation.basket.views.BasketFragment
import ru.papino.restaurant.presentation.menu.views.MenuFragment
import ru.papino.restaurant.presentation.orders.views.OrdersFragment
import ru.papino.restaurant.presentation.profile.views.ProfileFragment
import ru.papino.restaurant.presentation.registration.view.RegistrationFragment
import ru.papino.restaurant.presentation.settings.view.SettingsFragment

internal class ScreenManager private constructor() {

    init {
        Log.d(TAG, "init")
    }

    val menuFragment by lazy { MenuFragment.getInstance() }
    val basketFragment by lazy { BasketFragment.getInstance() }
    val authorizationFragment by lazy { AuthorizationFragment.getInstance() }
    val registrationManager by lazy { RegistrationFragment.getInstance() }
    val profileFragment by lazy { ProfileFragment.getInstance() }
    val ordersFragment by lazy { OrdersFragment.getInstance() }
    val settingsFragment by lazy { SettingsFragment.getInstance() }
    val aboutFragment by lazy { AboutFragment.getInstance() }


    companion object {
        private const val TAG = "ScreenManager"
        private val screenManager = ScreenManager()

        fun getManager() = screenManager
    }
}