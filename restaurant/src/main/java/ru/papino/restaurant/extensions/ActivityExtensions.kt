package ru.papino.restaurant.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.papino.restaurant.R
import ru.papino.restaurant.RestaurantActivity

internal fun AppCompatActivity.switchFragment(fragment: Fragment) {
    val fragmentManager = supportFragmentManager

    val currentFragment = fragmentManager.findFragmentByTag(RestaurantActivity.ACTIVE_FRAGMENT)
    if (currentFragment != null && currentFragment::class.java == fragment::class.java) {
        return
    }

    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(
        R.id.fragmentContainer,
        fragment,
        RestaurantActivity.ACTIVE_FRAGMENT
    )
    fragmentTransaction.commit()
}