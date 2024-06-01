package com.example.thesweetspotmobile.Network

import okhttp3.Interceptor
import okhttp3.Response
import android.util.Base64

class BasicAuthInterceptor(username: String, password: String) : Interceptor {

    private val credentials: String = Base64.encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Authorization", "Basic $credentials")
            .build()
        return chain.proceed(request)
    }
}