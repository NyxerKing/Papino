package ru.papino

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.papino.restaurant.R
import ru.papino.restaurant.databinding.ActivitySandboxBinding

class SandboxActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySandboxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySandboxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()
    }

    private fun setData() {
        with(binding) {
            editTextPhone.setMask(
                resources.getString(R.string.input_phone_mask),
                resources.getString(R.string.input_phone_pattern_check)
            )
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, SandboxActivity::class.java)
    }
}