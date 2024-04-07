package ru.papino.maps.listeners

import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map

internal class MapCameraListener(private val onUserMoveFinished: () -> Unit) : CameraListener {
    override fun onCameraPositionChanged(
        map: Map,
        position: CameraPosition,
        reason: CameraUpdateReason,
        finished: Boolean
    ) {
        if (reason == CameraUpdateReason.GESTURES) onUserMoveFinished()
    }
}