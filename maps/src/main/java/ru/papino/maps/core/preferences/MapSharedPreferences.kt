package ru.papino.maps.core.preferences

import android.content.Context
import com.yandex.mapkit.geometry.Point

internal class MapSharedPreferences private constructor(private val context: Context) {

    fun saveLocation(latitude: Double, longitude: Double) {
        getShared().edit()
            .apply {
                putString(KEY_MAP_USER_LOCATION_LATITUDE, latitude.toString())
                putString(KEY_MAP_USER_LOCATION_LONGITUDE, longitude.toString())
            }
            .apply()
    }

    fun saveLocation(position: Point) {
        saveLocation(latitude = position.latitude, longitude = position.longitude)
    }

    fun getUserLocation(): Point? {
        try {
            getShared().apply {
                val latitude = getString(KEY_MAP_USER_LOCATION_LATITUDE, "").orEmpty()
                val longitude = getString(KEY_MAP_USER_LOCATION_LONGITUDE, "").orEmpty()
                return Point(latitude.toDouble(), longitude.toDouble())
            }
        } catch (ex: Exception) {
            return null
        }
    }

    private fun getShared() =
        context.getSharedPreferences(KEY_MAP_DB, Context.MODE_PRIVATE)


    companion object {

        private const val KEY_MAP_DB = "databaseMap"
        private const val KEY_MAP_USER_LOCATION_LATITUDE = "KEY_MAP_USER_LOCATION_LATITUDE"
        private const val KEY_MAP_USER_LOCATION_LONGITUDE = "KEY_MAP_USER_LOCATION_LONGITUDE"

        fun getInstance(context: Context) = MapSharedPreferences(context)
    }
}