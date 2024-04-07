package ru.papino.maps.core.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.papino.maps.MapActivity

class AddressActivityContract : ActivityResultContract<String, String?>() {
    override fun createIntent(context: Context, input: String): Intent {
        return MapActivity.getInstance(context).putExtra(KEY, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return when {
            resultCode != Activity.RESULT_OK -> null
            else -> intent?.getStringExtra(KEY)
        }
    }

    companion object {
        const val KEY = "KEY_ADDRESS_RESULT"
    }
}