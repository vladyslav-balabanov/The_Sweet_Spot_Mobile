package com.example.thesweetspotmobile.Network.Requests
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginModel(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String?,
    @Json(name = "authMethod")
    val authMethod: Int
)