package com.example.sweetspot.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.sweetspot.R

class Decoder {
    companion object {
        fun decodeImage(context: Context, base64: String, defaultImage: Int): ImageBitmap {
            return try {
                if (base64.isEmpty()) {
                    BitmapFactory.decodeResource(context.resources, defaultImage).asImageBitmap()
                } else {
                    val bytes = Base64.decode(base64, Base64.DEFAULT)
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()
                        ?: BitmapFactory.decodeResource(context.resources, defaultImage)
                            .asImageBitmap()
                }
            } catch (e: Exception) {
                BitmapFactory.decodeResource(context.resources, defaultImage).asImageBitmap()
            }
        }

        fun decodeSimpleBase64(base64: String): Bitmap? {
            return try {
                val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}
