package com.example.thesweetspotmobile.Utils

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import javax.inject.Singleton

@Singleton
class TokenManager(context: Context) {
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun fetchToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun getUserId(token: String): String? {
        return try {
            val jwt = JWT(token)
            jwt.getClaim("Id").asString()
        } catch (e: Exception) {
            null
        }
    }

   fun clearToken() {
        sharedPreferences.edit().remove("token").apply()
    }
}