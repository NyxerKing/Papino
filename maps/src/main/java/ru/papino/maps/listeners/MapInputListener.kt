package ru.papino.maps.listeners

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import java.io.IOException
import java.util.Locale

internal class MapInputListener(
    private val context: Context,
    private val onMapTapAddress: (String?) -> Unit
) : InputListener {

    override fun onMapTap(p0: Map, p1: Point) {
        Log.d(TAG, "onMapTap")
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val address = geocoder.getFromLocation(p1.latitude, p1.longitude, 1)
            address?.forEach {
                Log.d(TAG, it.getAddressLine(0))
                onMapTapAddress(it.getAddressLine(0))
            }
        } catch (ex: IOException) {
            onMapTapAddress(null)
        }
    }

    override fun onMapLongTap(p0: Map, p1: Point) {
        Log.d(TAG, "onMapLongTap")
    }

    companion object {

        private const val TAG = "MapInputListener"
    }
}