package com.example.papino.presentation.regestration

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.papino.R
import com.example.papino.SharedKeys
import com.example.papino.net.ListUser
import com.example.papino.presentation.regestration.controlles.ControllerUser
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID


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


        val buttonReg: ImageButton = findViewById(R.id.buttonSaveRegistration)
        buttonReg.setOnClickListener { checkPasswordAndSaveBase() }

        val buttonBack: ImageView = findViewById(R.id.buttonBackRegMenu)

        buttonBack.setOnClickListener {
            onBackPressed()
        }
    }

    fun saveRegistratonUser(): Boolean {


        val editSurname = findViewById<EditText>(R.id.editSurname)
        val name = findViewById<EditText>(R.id.editName)
        val password = findViewById<EditText>(R.id.createPassword)
        val telephonenumber = findViewById<EditText>(R.id.editPhone)

        insertUser(
            editSurname.text.toString(), name.text.toString(),
            password.text.toString(), telephonenumber.text.toString(), "", ""
        )
        return true
    }

    private fun checkPasswordAndSaveBase(): Boolean {
        val password = findViewById<EditText>(R.id.createPassword).text

        if (password.length < 8) return false
        /*if (password.filter { it.isDigit() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
        if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false*/
        saveRegistratonUser()
        return true
    }

    @Throws(Exception::class)
    fun insertUser(
        surname: String, name: String, password: String, telephoneNumber: String,
        bonus: String, token: String
    ) {

        val controller = ControllerUser() {}
        controller.start(
            surname,
            name,
            telephoneNumber,
            password,
            "0",
            true
        )
        //addUserShared()
    }

    private fun addUserShared()
    {
        /*val sharedPreferences = this@RegistrationActivity.getSharedPreferences(
            SharedKeys.USER_DATA_KEY,
            Context.MODE_PRIVATE
        )

        val editJson = Gson().toJson(user)
        val edit = sharedPreferences.edit()
        edit.putString(SharedKeys.BASKED_ITEM_KEY, editJson)
        edit.commit()*/
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