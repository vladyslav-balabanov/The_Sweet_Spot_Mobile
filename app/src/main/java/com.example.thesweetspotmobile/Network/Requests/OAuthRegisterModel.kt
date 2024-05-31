package com.example.thesweetspotmobile.Network.Requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OAuthRegisterModel(
        @Json(name = "email")
        val email: String,
        @Json(name = "name")
        val name: String
)