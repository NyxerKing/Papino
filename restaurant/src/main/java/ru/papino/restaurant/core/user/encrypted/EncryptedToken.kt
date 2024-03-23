package ru.papino.restaurant.core.user.encrypted

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import ru.papino.restaurant.App
import ru.papino.restaurant.core.user.models.Token

internal object EncryptedToken {

    private const val ENCRYPTED_FILE_NAME = "papino_token"
    private const val ENCRYPTED_TOKEN_KEY = "papino_token_secret_key"

    /**
     * Сохранить токен
     *
     * @param context только applicationContext
     * @param token
     */
    fun save(context: Context, token: Token) {
        App().applicationContext
        getSharedPreferences(context)
            .edit()
            .apply {
                putString(ENCRYPTED_TOKEN_KEY, token.token)
            }.apply()
    }

    fun getToken(context: Context): Token {
        val sharedPreferences = getSharedPreferences(context)
        val token = sharedPreferences.getString(ENCRYPTED_TOKEN_KEY, "").orEmpty()
        return Token(token = token)
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
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