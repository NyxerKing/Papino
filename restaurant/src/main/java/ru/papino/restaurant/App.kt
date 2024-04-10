package ru.papino.restaurant

import android.app.Application
import android.content.Context

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        _context = applicationContext

        // MapKit SDK установка API-ключа
        // todo MapKitFactory.setApiKey("3dfe859c-091e-426d-a7ed-3227c018849a")
    }

    companion object {
        private var _context: Context? = null
        fun getApplicationContext() = _context
    }
}