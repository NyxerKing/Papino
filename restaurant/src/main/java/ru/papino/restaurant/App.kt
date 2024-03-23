package ru.papino.restaurant

import android.app.Application
import android.content.Context

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        _context = applicationContext
    }

    companion object {
        private var _context: Context? = null
        fun getApplicationContext() = _context
    }
}