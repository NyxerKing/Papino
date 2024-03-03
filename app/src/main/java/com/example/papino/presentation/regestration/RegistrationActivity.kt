package com.example.papino.presentation.regestration

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.papino.MainActivity
import com.example.papino.R
import com.example.papino.net.ListUser
import com.example.papino.net.User
import com.example.papino.presentation.menu.MenuActivity
import com.example.papino.presentation.regestration.controlles.ControllerUser
import com.example.papino.utils.WorkWithUser

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: RegistrationActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.item_registration_str)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)


        val buttonReg: ImageView = findViewById(R.id.buttonSave)
        buttonReg.setOnClickListener { checkPasswordAndSaveBase() }

        val buttonBack: ImageView = findViewById(R.id.buttonBackRegMenu)

        buttonBack.setOnClickListener {
            onBackPressed()
        }
    }

    fun saveRegistratonUser()  : Boolean {

        val password = findViewById<EditText>(R.id.createPassword)
        val editSurname = findViewById<EditText>(R.id.editSurname)
        val name = findViewById<EditText>(R.id.editName)
        val patronymic = findViewById<EditText>(R.id.createPassword)
        val telephonenumber = findViewById<EditText>(R.id.editPhone)
        val user = WorkWithUser(
            editSurname.text.toString(),
            name.text.toString(),
            patronymic.text.toString(),
            telephonenumber.text.toString(),
            password = password.text.toString()
        )


        // TODO: Слать в апи сохранение пользователя
        return true
    }

    private fun checkPasswordAndSaveBase () : Boolean
    {
        val password = findViewById<EditText>(R.id.createPassword).text

        if (password.length < 8) return false
        /*if (password.filter { it.isDigit() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
        if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false*/

        saveRegistratonUser();

        return  true
    }

    @Throws(Exception::class)
    fun insertUser(telephonenumber: String, password : String) {
            val controller = ControllerUser() {}
            controller.start(telephonenumber, password, false)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}