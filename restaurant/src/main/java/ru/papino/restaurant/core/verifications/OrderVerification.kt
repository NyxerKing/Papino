package ru.papino.restaurant.core.verifications

import ru.papino.restaurant.core.settings.CoreSettings
import ru.papino.restaurant.core.user.di.UserDI

internal class OrderVerification {

    /**
     * Разрешено сделать заказ
     */
    fun allowed() = UserDI.isUserInitializer() && CoreSettings.isPlaceOpen
}