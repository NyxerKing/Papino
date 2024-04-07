package ru.papino.maps.core

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

internal class MapPermissions(private val requestPermissionLauncher: ActivityResultLauncher<String>) {

    fun request(context: Context, onSuccess: () -> Unit) {
        when {
            check(context, ACCESS_FINE_LOCATION) && check(context, ACCESS_COARSE_LOCATION) -> {
                Log.d(TAG, "onSuccess()")
                onSuccess()
            }

            else -> {
                requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
                requestPermissionLauncher.launch(ACCESS_COARSE_LOCATION)
            }
        }
    }

    private fun check(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    companion object {
        private const val TAG = "MapPermissions"
    }
}