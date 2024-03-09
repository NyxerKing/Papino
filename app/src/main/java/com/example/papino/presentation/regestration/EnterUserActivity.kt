package com.example.papino.presentation.regestration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.papino.R
import com.example.papino.core.sharedPref.SharedKeys
import com.example.papino.databinding.ActivityEnterUserBinding
import com.example.papino.net.ListUser
import com.example.papino.presentation.menu.MenuActivity
import com.example.papino.presentation.regestration.controlles.ControllerUser
import com.google.gson.Gson

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
                getUser(
                    telephonenumber.getText().toString(),
                    password.getText().toString()
                )

            }

            buttonBackEnterUserMenu.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun controlUserEnter(telephonenumber: String, password: String): Boolean {
        if (telephonenumber.isNullOrEmpty() && password.isNullOrEmpty()) {
            val toast = Toast.makeText(
                applicationContext,
                "Для входа введите номер телефона и пароль",
                Toast.LENGTH_LONG
            )
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return false
        }

        if (telephonenumber.isNullOrEmpty()) {
            val toast = Toast.makeText(
                applicationContext,
                "Введите номер телефона",
                Toast.LENGTH_LONG
            )
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return false
        }
        if (password.isNullOrEmpty()) {
            val toast = Toast.makeText(
                applicationContext,
                "Введите пароль",
                Toast.LENGTH_LONG
            )
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return false
        }
        return true
    }

    @Throws(Exception::class)
    fun getUser(telephonenumber: String, password: String) {

        if (controlUserEnter(telephonenumber, password)) {
            val controller = ControllerUser(
                callBack = {  callBack, string ->
                    callBackUser = callBack
                if (callBackUser.group.size == 1) {
                    val intent = Intent(this@EnterUserActivity, MenuActivity::class.java)
                    messageForUser(true)
                    startActivity(intent)
                    finish()
                }
                if (callBackUser.group.size < 1 || callBackUser.group.size > 1) {
                    messageForUser(false)
                }
            } , callBackError = {}
            )
            controller.start("", "", telephonenumber, password, "",false)
        }
    }


    private fun addUserShared(user: ListUser)
    {

        if (user.group.size < 1) return
        val sharedPreferences = this@EnterUserActivity.getSharedPreferences(
            SharedKeys.USER_DATA_KEY,
            Context.MODE_PRIVATE
        )

        val editJson = Gson().toJson(user)
        val edit = sharedPreferences.edit()
        edit.putString(SharedKeys.BASKED_ITEM_KEY, editJson)
        edit.commit()
    }


    private fun messageForUser(enterUser : Boolean)
    {
        if (enterUser)
        {
            val toast = Toast.makeText(
                applicationContext,
                "Добро пожаловать в Papino, " + callBackUser.group[0].name,
                Toast.LENGTH_LONG
            )
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        else
        {
            val toast = Toast.makeText(
                applicationContext,
                "Пользователь не найден",
                Toast.LENGTH_LONG
            )
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()

        }
    }
}