package ru.papino

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import ru.papino.restaurant.RestaurantActivity
import ru.papino.restaurant.databinding.ActivityMainBinding
import ru.papino.uikit.extensions.fullscreen


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.fullscreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        object : CountDownTimer(500, 1000) {
            override fun onFinish() {
                startActivity(RestaurantActivity.newIntent(this@MainActivity))
                //startActivity(SandboxActivity.newIntent(this@MainActivity))
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {}
        }.start()
    }
}