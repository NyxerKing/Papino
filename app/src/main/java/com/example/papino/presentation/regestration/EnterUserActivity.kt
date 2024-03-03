package com.example.papino.presentation.regestration

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.papino.R
import com.example.papino.databinding.ActivityEnterUserBinding
import com.example.papino.net.ListUser
import com.example.papino.net.User
import com.example.papino.presentation.menu.MenuActivity
import com.example.papino.presentation.regestration.controlles.ControllerUser

class EnterUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnterUserBinding
    private lateinit var callBackUser: ListUser

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_enter_user)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)


        binding = ActivityEnterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonEnter.setOnClickListener {

                val telephonenumber: EditText = findViewById<EditText>(R.id.enterPhone2)
                val password: EditText = findViewById<EditText>(R.id.enterPassword)
                getUser(telephonenumber.getText().toString(), password.getText().toString())

            }

            buttonBackEnterUserMenu.setOnClickListener {
                onBackPressed()
            }
        }


    }

    private fun controlUserEnter(telephonenumber: String, password : String) : Boolean
    {
        if (telephonenumber.isNullOrEmpty() && password.isNullOrEmpty())
        {
            val toast = Toast.makeText(
                applicationContext,
                "Для входа введите номер телефона и пароль",
                Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return false
        }

        if (telephonenumber.isNullOrEmpty())
        {
            val toast = Toast.makeText(
                applicationContext,
                "Введите номер телефона",
                Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return false
        }
        if (password.isNullOrEmpty())
        {
            val toast = Toast.makeText(
                applicationContext,
                "Введите пароль",
                Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return false
        }
        return true
    }

    @Throws(Exception::class)
    fun getUser(telephonenumber: String, password : String) {

        if (controlUserEnter(telephonenumber, password)) {
            val controller = ControllerUser()
            {
                callBackUser = it
                if (callBackUser.group.size == 1) {
                    val intent = Intent(this@EnterUserActivity, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                if (callBackUser.group.size < 1 || callBackUser.group.size > 1) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Пользователь не найден",
                        Toast.LENGTH_SHORT
                    )
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            }
            controller.start(telephonenumber, password, false)
        }
    }
}