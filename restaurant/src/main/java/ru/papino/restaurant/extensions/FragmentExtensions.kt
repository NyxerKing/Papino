package ru.papino.restaurant.extensions

import androidx.fragment.app.Fragment
import ru.papino.restaurant.R
import ru.papino.restaurant.RestaurantActivity

internal fun Fragment.switchFragment(fragment: Fragment) {
    val fragmentTransaction = parentFragmentManager.beginTransaction()
    fragmentTransaction.replace(
        R.id.fragmentContainer, fragment,
        RestaurantActivity.ACTIVE_FRAGMENT
    )
    fragmentTransaction.commit()
}