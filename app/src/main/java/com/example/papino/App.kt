package com.example.papino

import android.app.Application
import com.example.papino.net.User

class App : Application() {

    private var user: User? = null


    fun setUser(user: User) {
        this.user = user
    }

    fun getUser() = user
}