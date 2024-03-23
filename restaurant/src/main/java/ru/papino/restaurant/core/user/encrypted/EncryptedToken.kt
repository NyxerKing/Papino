package ru.papino.restaurant.core.user.encrypted

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import ru.papino.restaurant.App
import ru.papino.restaurant.core.user.models.Token

internal object EncryptedToken {

    private const val ENCRYPTED_FILE_NAME = "papino_token"
    private const val ENCRYPTED_TOKEN_KEY = "papino_token_secret_key"

    fun save(token: Token) {
        getSharedPreferences()
            .edit()
            .apply {
                putString(ENCRYPTED_TOKEN_KEY, token.token)
            }.apply()
    }

    fun getToken(): Token? {
        val sharedPreferences = getSharedPreferences()
        val token = sharedPreferences.getString(ENCRYPTED_TOKEN_KEY, "")
        token?.let {
            return Token(token = it)
        } ?: return null
    }

    private fun getSharedPreferences(): SharedPreferences {
        val context = App().applicationContext
        val masterKey: MasterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            ENCRYPTED_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}