package com.example.papino

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.papino.core.sharedPref.SharedKeys
import com.example.papino.databinding.ActivityMainBinding
import com.example.papino.presentation.menu.MenuActivity
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferencesClear = this@MainActivity.getSharedPreferences(
            SharedKeys.BASKED_DATA_KEY,
            Context.MODE_PRIVATE
        )
        sharedPreferencesClear.edit().clear().commit();

        object : CountDownTimer(2500, 1000) {
            override fun onFinish() {
                val intent = Intent(this@MainActivity, MenuActivity::class.java)
                startActivity(intent)
            }

            override fun onTick(millisUntilFinished: Long) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start()

        /*with(binding) {
            buttonEnterUserMenu.setOnClickListener {
                val intent = Intent(this@MainActivity, EnterUserActivity::class.java)
                startActivity(intent)
            }

            buttonRegistrationMenu.setOnClickListener {
                val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
                startActivity(intent)
            }

            imgBasketMainActivity.setOnClickListener {
                val intent = Intent(this@MainActivity, MenuActivity::class.java)
                startActivity(intent)
            }
            checkUserTokenShared()
        }*/
    }

    private fun checkUserTokenShared()
    {
        val sharedPreferences = this@MainActivity.getSharedPreferences(
            SharedKeys.USER_DATA_KEY,
            Context.MODE_PRIVATE
        )

        val json = sharedPreferences.getString(SharedKeys.USER_ITEM_KEY, "")
        val editJson = Gson().toJson(json)
    }
}