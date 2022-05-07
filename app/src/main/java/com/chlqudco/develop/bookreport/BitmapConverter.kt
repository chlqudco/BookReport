package com.chlqudco.develop.bookreport

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import java.io.ByteArrayOutputStream
import java.lang.*
import java.util.*


class BitmapConverter {
    // Bitmap -> String
    fun bitmapToString(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        val bytes = stream.toByteArray()

        return Base64.getEncoder().encodeToString(bytes)
    }

    // String -> Bitmap
    fun stringToBitmap(encodedString: String?): Bitmap? {
        val encodeByte: ByteArray = Base64.getDecoder().decode(encodedString)
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }
}