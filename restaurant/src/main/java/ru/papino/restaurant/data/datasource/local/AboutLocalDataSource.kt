package ru.papino.restaurant.data.datasource.local

import android.util.Log
import ru.papino.restaurant.data.datasource.net.models.AboutJsonModel

internal class AboutLocalDataSource private constructor() {

    private var aboutLocalModel: AboutJsonModel? = null

    fun setBuffer(data: AboutJsonModel) {
        aboutLocalModel = data
        Log.d(TAG, "set buffer")
    }

    fun getBuffer(): AboutJsonModel? {
        Log.d(TAG, "get buffer = $aboutLocalModel")
        return aboutLocalModel
    }

    companion object {
        private const val TAG = "AboutLocalDataSource"
        private val localObject = AboutLocalDataSource()

        fun getInstance() = localObject
    }
}