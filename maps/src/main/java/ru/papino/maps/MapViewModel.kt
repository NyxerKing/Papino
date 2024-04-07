package ru.papino.maps

import androidx.lifecycle.ViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map

internal class MapViewModel : ViewModel() {

    fun openMyLocation(map: Map, location: Location) {
        map.move(
            CameraPosition(location.position, 18.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 1F),
            null
        )
    }

    fun openMyLocation(map: Map, point: Point) {
        map.move(
            CameraPosition(point, 18.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 1F),
            null
        )
    }
}