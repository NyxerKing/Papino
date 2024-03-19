package ru.papino.restaurant

import ru.papino.restaurant.presentation.authorization.views.AuthorizationFragment
import ru.papino.restaurant.presentation.basket.views.BasketFragment
import ru.papino.restaurant.presentation.menu.views.MenuFragment
import ru.papino.restaurant.presentation.orders.views.OrdersFragment
import ru.papino.restaurant.presentation.profile.views.ProfileFragment
import ru.papino.restaurant.presentation.registration.view.RegistrationFragment

internal class ScreenManager private constructor() {

    val menuFragment by lazy { MenuFragment() }
    val basketFragment by lazy { BasketFragment() }
    val authorizationFragment by lazy { AuthorizationFragment() }
    val registrationManager by lazy { RegistrationFragment() }
    val profileFragment by lazy { ProfileFragment() }
    val ordersFragment by lazy { OrdersFragment() }


    companion object {
        fun getManager() = ScreenManager()
    }
}