package ru.papino.restaurant.data.datasource.local.impl

import android.content.res.Resources
import ru.papino.restaurant.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter

internal class LocalDataSource private constructor(private val resource: Resources) {

    fun getData(): String {
        val inputStream = resource.openRawResource(R.raw.data_menu)
        val writer = StringWriter()
        val buffer = CharArray(1024)
        inputStream.use { rawData ->
            val reader = BufferedReader(InputStreamReader(rawData, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }

        return writer.toString()
    }

    companion object {
        fun getInstance(resource: Resources) = LocalDataSource(resource = resource)
    }
}