package ru.papino.maps.listeners

import android.util.Log
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus

internal class MapLocationListener(
    private val onLocation: (Location) -> Unit
) : LocationListener {
    override fun onLocationUpdated(location: Location) {
        Log.d(TAG, "longitude = ${location.position.longitude}")
        Log.d(TAG, "latitude = ${location.position.latitude}")

        onLocation(location)
    }

    override fun onLocationStatusUpdated(locationStatus: LocationStatus) {

    }

    companion object {
        private const val TAG = "PapinoLocationListener"
    }
}